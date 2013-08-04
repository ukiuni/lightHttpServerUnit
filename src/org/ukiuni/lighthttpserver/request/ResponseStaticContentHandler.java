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
		System.out.println("request path:" + path);
		final File responseFile = new File(baseDir, path.substring(1));
		System.out.println("file is "+responseFile.getAbsolutePath());
		if (responseFile.isFile()) {
			return new Response() {
				@Override
				public void onResponse(OutputStream responseOutputStream) throws Throwable {
					write(responseOutputStream, 200, responseFile);
				}
			};
		}
		return new Response404();
	}

	@Override
	public void onException(Throwable e) {
		// TODO Auto-generated method stub

	}

}
