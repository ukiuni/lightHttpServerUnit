package org.ukiuni.lighthttpserver.response;

import java.io.OutputStream;

import org.ukiuni.lighthttpserver.response.Response;

public class Response404 extends Response {

	@Override
	public void onResponse(OutputStream responseOutputStream) throws Throwable {
		write(responseOutputStream, 404, "no such resource", "no such resource", "UTF-8");
	}

}
