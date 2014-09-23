package com.jskierbi.robospice;

import android.app.Application;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.Persister;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.file.InFileObjectPersisterFactory;

public class JSSpiceService extends SpiceService {

	private static final String TAG = JSSpiceService.class.getSimpleName();

	@Override public CacheManager createCacheManager(Application application) throws CacheCreationException {

		// Use SMILE
		ObjectMapper objectMapper = new ObjectMapper(new SmileFactory());
		Persister persister = null;
		try {
			InFileObjectPersisterFactory factory = new JacksonObjectPersisterFactory(
					application,
					objectMapper,
					null,
					null);
			factory.setAsyncSaveEnabled(true);
			persister = factory;
		} catch (CacheCreationException e) {
			Log.wtf(TAG, "Cannot create cache!");
		}

		CacheManager cacheManager = new CacheManager();
		if (persister != null) {
			cacheManager.addPersister(persister);
		}
		return cacheManager;
	}
}
