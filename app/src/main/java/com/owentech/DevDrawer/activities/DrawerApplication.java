package com.owentech.DevDrawer.activities;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by jakub on 09/28/2014.
 */
public class DrawerApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();

		// Custom package loading
		PreferenceManager.getDefaultSharedPreferences(this).edit()
				.putString("syncUrl","https://www.dropbox.com/s/wbjufg8vqutchsa/package_names?dl=1")
				.putBoolean("flgEnableSync", true)
				.commit();
	}
}
