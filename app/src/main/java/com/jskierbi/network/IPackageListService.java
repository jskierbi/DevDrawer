package com.jskierbi.network;

import retrofit.http.GET;

public interface IPackageListService {

	@GET("/s/r6xl8nhzr3wl9p6/packages.json?dl=1")
	public StringList listPackages();
}
