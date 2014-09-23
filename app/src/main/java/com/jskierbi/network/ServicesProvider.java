package com.jskierbi.network;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by jakub on 09/23/2014.
 */
public class ServicesProvider {

	private static RestAdapter sRestAdapter;
	private static IPackageListService sPackageListService;
	public static IPackageListService getPackageListService() {

		if (sRestAdapter == null) {
			sRestAdapter = new RestAdapter.Builder()
					.setEndpoint("https://www.dropbox.com")
					.setLogLevel(RestAdapter.LogLevel.FULL)
					.setLog(new AndroidLog("NETWORK"))
					.build();
		}

		if (sPackageListService == null) {
			sPackageListService = sRestAdapter.create(IPackageListService.class);
		}

		return sPackageListService;
	}
}
