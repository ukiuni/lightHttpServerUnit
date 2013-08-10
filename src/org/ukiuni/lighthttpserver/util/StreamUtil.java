package org.ukiuni.lighthttpserver.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
	private static final int BUFFER_SIZE = 1024;

	public static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int readed = in.read(buffer);
		while (readed > 0) {
			out.write(buffer, 0, readed);
			readed = in.read(buffer);
		}
	}

	public static String streamToString(InputStream in, String encode) throws IOException {
		return new String(inputStreamToByteArray(in), encode);
	}

	public static void streamToFile(InputStream in, File file) throws IOException {
		file.getParentFile().mkdirs();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			copy(in, out);
		} finally {
			closeQuietry(out);
		}
	}

	public static byte[] inputStreamToByteArray(InputStream in) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int readed = in.read(buffer);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while (readed > 0) {
			out.write(buffer, 0, readed);
			readed = in.read(buffer);
		}
		byte[] returnBytes = out.toByteArray();
		out.reset();
		return returnBytes;
	}

	public static void closeQuietry(Closeable in) {
		try {
			in.close();
		} catch (Throwable e) {
		}
	}

	public static String fileToString(File file, String encode) throws IOException {
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamUtil.copy(in, out);

		return new String(out.toByteArray(), encode);
	}
}
