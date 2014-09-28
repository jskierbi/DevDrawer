package com.jskierbi.network;

import com.octo.android.robospice.request.SpiceRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.Arrays;

public class RequestPackageList extends SpiceRequest<StringList> {

	private final String mUrl;

	public RequestPackageList(String url) {
		super(StringList.class);
		mUrl = url;
	}

	@Override public StringList loadDataFromNetwork() throws Exception {
		Request request = new Request.Builder()
				.url(mUrl)
				.build();
		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		StringList strList = new StringList();
		String[] array = response.body().string().replace("\r\n", "\n").split("\n");
		strList.addAll(Arrays.asList(array));
		return strList;
	}
}
