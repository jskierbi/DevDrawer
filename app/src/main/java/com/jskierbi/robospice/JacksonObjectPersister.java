package com.jskierbi.robospice;

import android.app.Application;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.exception.CacheLoadingException;
import com.octo.android.robospice.persistence.exception.CacheSavingException;
import com.octo.android.robospice.persistence.file.InFileObjectPersister;
import roboguice.util.temp.Ln;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jakub on 09/23/2014.
 *
 * Based on RoboSpice Jackson2ObjectPersister
 * @see https://github.com/stephanenicolas/robospice/blob/release/extensions/robospice-spring-android-parent/robospice-spring-android/src/main/java/com/octo/android/robospice/persistence/springandroid/json/jackson2/Jackson2ObjectPersister.java
 */
public class JacksonObjectPersister<T> extends InFileObjectPersister<T> {

	private static final String TAG = JacksonObjectPersister.class.getSimpleName();
	private final ObjectMapper mObjectMapper;

	public JacksonObjectPersister(Application application, Class<T> clazz, ObjectMapper mapper, File cacheFolder) throws CacheCreationException {
		super(application, clazz, cacheFolder);
		this.mObjectMapper = mapper;
	}

	public JacksonObjectPersister(Application application, Class<T> clazz) throws CacheCreationException {
		this(application, clazz, new ObjectMapper(), null);
	}

	@Override protected T readCacheDataFromFile(File file) throws CacheLoadingException {
		try {
			return mObjectMapper.readValue(file, getHandledClass());
		} catch (FileNotFoundException e) {
			// Should not occur (we test before if file exists)
			// Do not throw, file is not cached
			Log.w(TAG, "RoboSpice CACHE: file " + file.getAbsolutePath() + " does not exists", e);
			return null;
		} catch (Exception e) {
			Log.w(TAG, "RoboSpice CACHE: cannot read! ", e);
			throw new CacheLoadingException(e);
		}
	}

	@Override public T saveDataToCacheAndReturnData(final T data, final Object cacheKey) throws CacheSavingException {
		Ln.v("Saving String " + data + " into cacheKey = " + cacheKey);
		try {
			if (isAsyncSaveEnabled()) {

				Thread t = new Thread() {
					@Override
					public void run() {
						try {
							writeDataToCache(data, cacheKey);
						} catch (IOException | CacheSavingException e) {
							Ln.e(e, "An error occured on saving request " + cacheKey + " data asynchronously");
						}
					}
					;
				};
				t.start();
			} else {
				writeDataToCache(data, cacheKey);
			}
		} catch (Exception e) {
			Log.w(TAG, "RoboSpice CACHE: cannot save! ", e);
			throw new CacheSavingException(e);
		}
		return data;
	}

	private void writeDataToCache(T data, Object cacheKey) throws IOException, CacheSavingException {
		mObjectMapper.writeValue(getCacheFile(cacheKey), data);
	}
}
