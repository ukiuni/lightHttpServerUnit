package org.ukiuni.lighthttpserver.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ukiuni.lighthttpserver.util.FileUtil;
import org.ukiuni.lighthttpserver.util.StreamUtil;
import org.ukiuni.lighthttpserver.util.TimeUtil;

public abstract class Response {
	private static final Map<Integer, String> RESPONSE_CODE_MAP = new HashMap<Integer, String>();
	private static final String SERVER_STRING = "LightHttpServer 0.0.001";
	private Map<String, String> additionalHeader;
	private boolean modeAsync = false;

	public Map<String, String> getAdditionalHeader() {
		return additionalHeader;
	}

	public void setAdditionalHeader(Map<String, String> additionalHeader) {
		this.additionalHeader = additionalHeader;
	}

	public void write(OutputStream out, int responseCode, String value, String contentType, String charset) throws IOException {
		try {
			byte[] responseData = value.getBytes(charset);
			writeHeader(out, responseCode, contentType, responseData.length);
			out.write(responseData);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public void write(OutputStream out, int responseCode, File file) throws IOException {
		write(out, responseCode, file, FileUtil.getMimeType(file));
	}

	public void write(OutputStream out, int responseCode, File file, String contentType) throws IOException {
		try {
			if (file.isFile()) {
				FileInputStream in = new FileInputStream(file);
				writeHeader(out, responseCode, contentType, file.length());
				StreamUtil.copy(in, out);
			} else {
				write(out, 404, "no such resource", "no such resource", "UTF-8");
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private void writeHeader(OutputStream out, int responseCode, String contentType, long contentLength) throws IOException, UnsupportedEncodingException {
		out.write(("HTTP/1.1 " + responseCode + " " + RESPONSE_CODE_MAP.get(responseCode) + "\r\n").getBytes("UTF-8"));
		out.write(("Date: " + headerReplace(TimeUtil.getUTC()) + "\r\n").getBytes("UTF-8"));
		out.write(("Server: " + headerReplace(SERVER_STRING) + "\r\n").getBytes("UTF-8"));
		out.write(("Content-Type: " + headerReplace(contentType) + "\r\n").getBytes("UTF-8"));
		out.write(("Content-Length: " + headerReplace(String.valueOf(contentLength)) + "\r\n").getBytes("UTF-8"));
		if (null != additionalHeader) {
			Set<String> keySet = additionalHeader.keySet();
			for (String key : keySet) {
				out.write((key + ": " + headerReplace(additionalHeader.get(key)) + "\r\n").getBytes("UTF-8"));
			}
		}
		out.write("\r\n".getBytes());
	}

	private String headerReplace(String src) {
		if (null == src) {
			return null;
		}
		return src.replace("\n", "%0A").replace("\r", "%0D");
	}

	static {
		RESPONSE_CODE_MAP.put(101, "Switching Protocols");
		RESPONSE_CODE_MAP.put(200, "OK");
		RESPONSE_CODE_MAP.put(201, "Created");
		RESPONSE_CODE_MAP.put(202, "Accepted");
		RESPONSE_CODE_MAP.put(203, "Non-Authoritative Information");
		RESPONSE_CODE_MAP.put(204, "No Content");
		RESPONSE_CODE_MAP.put(205, "Reset Content");
		RESPONSE_CODE_MAP.put(206, "Partial Content");
		RESPONSE_CODE_MAP.put(300, "Multiple Choices");
		RESPONSE_CODE_MAP.put(301, "Moved Permanently");
		RESPONSE_CODE_MAP.put(302, "Moved Temporarily");
		RESPONSE_CODE_MAP.put(303, "See Other");
		RESPONSE_CODE_MAP.put(304, "Not Modified");
		RESPONSE_CODE_MAP.put(305, "Use Proxy");
		RESPONSE_CODE_MAP.put(400, "Bad Request");
		RESPONSE_CODE_MAP.put(401, "Unauthorized");
		RESPONSE_CODE_MAP.put(402, "Payment Required");
		RESPONSE_CODE_MAP.put(403, "Forbidden");
		RESPONSE_CODE_MAP.put(404, "Not Found");
		RESPONSE_CODE_MAP.put(405, "Method Not Allowed");
		RESPONSE_CODE_MAP.put(406, "Not Acceptable");
		RESPONSE_CODE_MAP.put(407, "Proxy Authentication Required");
		RESPONSE_CODE_MAP.put(408, "Request Time-out");
		RESPONSE_CODE_MAP.put(409, "Conflict");
		RESPONSE_CODE_MAP.put(410, "Gone");
		RESPONSE_CODE_MAP.put(411, "Length Required");
		RESPONSE_CODE_MAP.put(412, "Precondition Failed");
		RESPONSE_CODE_MAP.put(413, "Request Entity Too Large");
		RESPONSE_CODE_MAP.put(414, "Request-URI Too Large");
		RESPONSE_CODE_MAP.put(415, "Unsupported Media Type");
		RESPONSE_CODE_MAP.put(500, "Internal Server Error");
		RESPONSE_CODE_MAP.put(501, "Not Implemented");
		RESPONSE_CODE_MAP.put(502, "Bad Gateway");
		RESPONSE_CODE_MAP.put(503, "Service Unavailable");
		RESPONSE_CODE_MAP.put(504, "Gateway Time-out");
		RESPONSE_CODE_MAP.put(505, "HTTP Version not supported");
	}

	public void toAsyncMode() {
		modeAsync = true;
	}

	public boolean isAsyncMode() {
		return modeAsync;
	}

	public abstract void onResponse(OutputStream responseOutputStream) throws Throwable;
}
