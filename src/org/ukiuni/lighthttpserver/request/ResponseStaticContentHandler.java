package org.ukiuni.lighthttpserver.request;

import java.io.File;
import java.io.OutputStream;

import org.ukiuni.lighthttpserver.response.Response;

public class ResponseStaticContentHandler extends Handler {
	private File baseDir;

	public ResponseStaticContentHandler(String baseDirPath) {
		baseDir = new File(baseDirPath);
	}

	@Override
	public Response onRequest(Request request) {
		String path = request.getPath();
		if (path.endsWith("/")) {
			path = path + "index.html";
		}
		final File responseFile = new File(baseDir, path.substring(1));
		if (responseFile.isFile()) {
			return new Response() {
				@Override
				public void onResponse(OutputStream responseOutputStream) throws Throwable {
					write(responseOutputStream, getResponseCode(responseFile), responseFile);
				}
			};
		}
		return new Response404();
	}

	@Override
	public void onException(Throwable e) {

	}

	protected int getResponseCode(File responseFile) {
		return 200;
	}

}
