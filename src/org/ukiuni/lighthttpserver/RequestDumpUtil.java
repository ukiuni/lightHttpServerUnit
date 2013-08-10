package org.ukiuni.lighthttpserver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.ukiuni.lighthttpserver.util.StreamUtil;

public class RequestDumpUtil {
	public static void main(String[] args) throws IOException, InterruptedException {
		String urlString = args[1];

		String paramMethod = null;
		String paramOutFile = null;
		String paramEncode = null;
		String paramOutDir = null;
		for (int i = 0; i < args.length; i++) {
			if ("-m".equals(args[i])) {
				paramMethod = args[i + 1];
			} else if ("-o".equals(args[i])) {
				paramOutFile = args[i + 1];
			} else if ("-e".equals(args[i])) {
				paramEncode = args[i + 1];
			} else if ("-d".equals(args[i])) {
				paramOutDir = args[i + 1];
			}
		}

		dump(urlString, paramMethod, paramOutFile, paramEncode, paramOutDir);
	}

	public static void dump(String urlString) throws IOException {
		dump(urlString, null, null, null, null);
	}

	public static void dump(String urlString, String paramMethod, String paramOutFile, String paramEncode, String paramOutDir) throws IOException {
		URL url = new URL(urlString);
		String parameter = url.getQuery();
		String outFile = url.getPath();
		String method = "GET";
		String encode = "UTF-8";
		String outDir = ".";
		if (null != paramMethod) {
			method = paramMethod;
		}
		if (null != paramOutFile) {
			outFile = paramOutFile;
		}
		if (null != paramEncode) {
			encode = paramEncode;
		}
		if (null != paramOutDir) {
			outDir = paramOutDir;
		}
		HttpURLConnection connection;
		if ("GET".equals(method) || "DELETE".equals(method)) {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			StreamUtil.streamToFile(connection.getInputStream(), new File(new File(outDir), outFile));
			connection.getResponseCode();
			connection.disconnect();
		} else if ("POST".equals(method) || "PUT".equals(method)) {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			out.write(parameter.getBytes(encode));
			out.close();
			StreamUtil.streamToFile(connection.getInputStream(), new File(new File(outDir), outFile));
			connection.getResponseCode();
			connection.disconnect();
		} else {
			throw new IllegalArgumentException("unknown method " + method);
		}
	}
}
