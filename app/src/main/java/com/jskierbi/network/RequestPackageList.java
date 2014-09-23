package com.jskierbi.network;

import com.octo.android.robospice.request.SpiceRequest;

public class RequestPackageList extends SpiceRequest<StringList> {

	public RequestPackageList() {
		super(StringList.class);
	}

	@Override public StringList loadDataFromNetwork() throws Exception {

		IPackageListService packageListService = ServicesProvider.getPackageListService();
		StringList result = packageListService.listPackages();
		return result;
	}
}
