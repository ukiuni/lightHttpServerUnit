package org.ukiuni.lighthttpserver.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.ukiuni.lighthttpserver.response.Response;

public class DefaultHandler extends Handler {
	private PrintStream errorOut = System.err;

	public PrintStream getErrorOut() {
		return errorOut;
	}

	public void setErrorOut(PrintStream errorOut) {
		this.errorOut = errorOut;
	}

	private Map<String, ReturnValue> returnMap = new HashMap<String, ReturnValue>();
	{
		returnMap.put("/", new ReturnValue(200, "text/html; charset=UTF-8", "<html><body><H1>Hello! This is LightHttpServer</H1></body><html>", "UTF-8"));
	}

	public DefaultHandler addResponse(String path, String value) {
		returnMap.put(path, new ReturnValue(200, "text/html; charset=UTF-8", value, "UTF-8"));
		return this;
	}

	public DefaultHandler addResponse(String path, String value, String contentType) {
		returnMap.put(path, new ReturnValue(200, contentType, value, "UTF-8"));
		return this;
	}

	public DefaultHandler addResponse(String path, String value, String contentType, String encode) {
		returnMap.put(path, new ReturnValue(200, contentType, value, encode));
		return this;
	}

	public DefaultHandler addResponse(int responseCode, String path, String value, String contentType, String encode) {
		returnMap.put(path, new ReturnValue(responseCode, contentType, value, encode));
		return this;
	}

	@Override
	public Response onRequest(Request request) {
		ReturnValue returnValue = returnMap.get(request.getPath());
		if (null != returnValue) {
			return new ReturnValueSettableResponse(returnValue);
		}
		return new Response404();
	}

	public class ReturnValueSettableResponse extends Response {
		ReturnValue returnValue;

		public ReturnValueSettableResponse(ReturnValue returnValue) {
			this.returnValue = returnValue;
		}

		@Override
		public void onResponse(OutputStream out) {
			try {
				write(out, returnValue.responseCode, returnValue.contentType, returnValue.value, returnValue.encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ReturnValue {
		public ReturnValue(int responseCode, String contentType, String value, String encode) {
			this.responseCode = responseCode;
			this.contentType = contentType;
			this.value = value;
			this.encode = encode;
		}

		public int responseCode;
		public String contentType;
		public String value;
		public String encode;
	}

	@Override
	public void onException(Throwable e) {
		e.printStackTrace(errorOut);
	}
}
