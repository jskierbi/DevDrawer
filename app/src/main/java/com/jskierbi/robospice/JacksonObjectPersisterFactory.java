package com.jskierbi.robospice;

import android.app.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.file.InFileObjectPersister;
import com.octo.android.robospice.persistence.file.InFileObjectPersisterFactory;

import java.io.File;
import java.util.List;

/**
 * Created by jakub on 09/23/2014.
 *
 * Based on RoboSpice Jackson2ObjectPersisterFactory
 * @see https://github.com/stephanenicolas/robospice/blob/release/extensions/robospice-spring-android-parent/robospice-spring-android/src/main/java/com/octo/android/robospice/persistence/springandroid/json/jackson2/Jackson2ObjectPersisterFactory.java
 */
public class JacksonObjectPersisterFactory extends InFileObjectPersisterFactory {

	private final ObjectMapper mObjectMapper;

	public JacksonObjectPersisterFactory(
			Application application,
			ObjectMapper objectMapper,
			List<Class<?>> listHandledClasses,
			File cacheFolder)
			throws CacheCreationException {
		super(application, listHandledClasses, cacheFolder);
		mObjectMapper = objectMapper;
	}

	@Override
	public <DATA> InFileObjectPersister<DATA> createInFileObjectPersister(Class<DATA> clazz, File cacheFolder)
			throws CacheCreationException {
		return new JacksonObjectPersister<DATA>(getApplication(), clazz, mObjectMapper, cacheFolder);
	}

}
