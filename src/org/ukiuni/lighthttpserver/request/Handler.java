package org.ukiuni.lighthttpserver.request;

import org.ukiuni.lighthttpserver.response.Response;

public abstract class Handler {
	private String requestPaseEncode = "UTF-8";

	public String getRequestPaseEncode() {
		return requestPaseEncode;
	}

	public void setRequestPaseEncode(String requestPaseEncode) {
		this.requestPaseEncode = requestPaseEncode;
	}

	public abstract Response onRequest(Request request);

	public abstract void onException(Throwable e);
}
