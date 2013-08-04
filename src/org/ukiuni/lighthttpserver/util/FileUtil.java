package org.ukiuni.lighthttpserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class FileUtil {
	public static void deleteAll(String filePath) {
		deleteAll(new File(filePath));
	}

	public static void deleteAll(File file) {
		if (file.isDirectory()) {
			for (File childlen : file.listFiles()) {
				deleteAll(childlen);
			}
		}
		file.delete();
	}

	public static byte[] load(File file) throws IOException {
		FileInputStream fileIn = new FileInputStream(file);
		byte[] fileData = StreamUtil.inputStreamToByteArray(fileIn);
		StreamUtil.closeQuietry(fileIn);
		return fileData;
	}

	private static Map<String, String> extentionMimetypeMap = new HashMap<String, String>();
	static {
		extentionMimetypeMap.put("ai", "application/postscript");
		extentionMimetypeMap.put("aif", "audio/x-aiff");
		extentionMimetypeMap.put("aifc", "audio/x-aiff");
		extentionMimetypeMap.put("aiff", "audio/x-aiff");
		extentionMimetypeMap.put("asc", "text/plain");
		extentionMimetypeMap.put("au", "audio/basic");
		extentionMimetypeMap.put("avi", "video/x-msvideo");
		extentionMimetypeMap.put("bcpio", "application/x-bcpio");
		extentionMimetypeMap.put("bin", "application/octet-stream");
		extentionMimetypeMap.put("bmp", "image/bmp");
		extentionMimetypeMap.put("bz2", "application/x-bzip2");
		extentionMimetypeMap.put("cdf", "application/x-netcdf");
		extentionMimetypeMap.put("chrt", "application/x-kchart");
		extentionMimetypeMap.put("class", "application/octet-stream");
		extentionMimetypeMap.put("cpio", "application/x-cpio");
		extentionMimetypeMap.put("cpt", "application/mac-compactpro");
		extentionMimetypeMap.put("csh", "application/x-csh");
		extentionMimetypeMap.put("css", "text/css");
		extentionMimetypeMap.put("dcr", "application/x-director");
		extentionMimetypeMap.put("dir", "application/x-director");
		extentionMimetypeMap.put("djv", "image/vnd.djvu");
		extentionMimetypeMap.put("djvu", "image/vnd.djvu");
		extentionMimetypeMap.put("dll", "application/octet-stream");
		extentionMimetypeMap.put("dms", "application/octet-stream");
		extentionMimetypeMap.put("doc", "application/msword");
		extentionMimetypeMap.put("dvi", "application/x-dvi");
		extentionMimetypeMap.put("dxr", "application/x-director");
		extentionMimetypeMap.put("eps", "application/postscript");
		extentionMimetypeMap.put("etx", "text/x-setext");
		extentionMimetypeMap.put("exe", "application/octet-stream");
		extentionMimetypeMap.put("ez", "application/andrew-inset");
		extentionMimetypeMap.put("gif", "image/gif");
		extentionMimetypeMap.put("gtar", "application/x-gtar");
		extentionMimetypeMap.put("gz", "application/x-gzip");
		extentionMimetypeMap.put("hdf", "application/x-hdf");
		extentionMimetypeMap.put("hqx", "application/mac-binhex40");
		extentionMimetypeMap.put("htm", "text/html");
		extentionMimetypeMap.put("html", "text/html");
		extentionMimetypeMap.put("ice", "x-conference/x-cooltalk");
		extentionMimetypeMap.put("ief", "image/ief");
		extentionMimetypeMap.put("iges", "model/iges");
		extentionMimetypeMap.put("igs", "model/iges");
		extentionMimetypeMap.put("jpe", "image/jpeg");
		extentionMimetypeMap.put("jpeg", "image/jpeg");
		extentionMimetypeMap.put("jpg", "image/jpeg");
		extentionMimetypeMap.put("js", "application/x-javascript");
		extentionMimetypeMap.put("kar", "audio/midi");
		extentionMimetypeMap.put("kil", "application/x-killustrator");
		extentionMimetypeMap.put("kpr", "application/x-kpresenter");
		extentionMimetypeMap.put("kpt", "application/x-kpresenter");
		extentionMimetypeMap.put("ksp", "application/x-kspread");
		extentionMimetypeMap.put("kwd", "application/x-kword");
		extentionMimetypeMap.put("kwt", "application/x-kword");
		extentionMimetypeMap.put("latex", "application/x-latex");
		extentionMimetypeMap.put("lha", "application/octet-stream");
		extentionMimetypeMap.put("lzh", "application/octet-stream");
		extentionMimetypeMap.put("m3u", "audio/x-mpegurl");
		extentionMimetypeMap.put("man", "application/x-troff-man");
		extentionMimetypeMap.put("me", "application/x-troff-me");
		extentionMimetypeMap.put("mesh", "model/mesh");
		extentionMimetypeMap.put("mid", "audio/midi");
		extentionMimetypeMap.put("midi", "audio/midi");
		extentionMimetypeMap.put("mif", "application/vnd.mif");
		extentionMimetypeMap.put("mov", "video/quicktime");
		extentionMimetypeMap.put("movie", "video/x-sgi-movie");
		extentionMimetypeMap.put("mp2", "audio/mpeg");
		extentionMimetypeMap.put("mp3", "audio/mpeg");
		extentionMimetypeMap.put("mpe", "video/mpeg");
		extentionMimetypeMap.put("mpeg", "video/mpeg");
		extentionMimetypeMap.put("mpg", "video/mpeg");
		extentionMimetypeMap.put("mpga", "audio/mpeg");
		extentionMimetypeMap.put("ms", "application/x-troff-ms");
		extentionMimetypeMap.put("msh", "model/mesh");
		extentionMimetypeMap.put("mxu", "video/vnd.mpegurl");
		extentionMimetypeMap.put("nc", "application/x-netcdf");
		extentionMimetypeMap.put("oda", "application/oda");
		extentionMimetypeMap.put("ogg", "application/ogg");
		extentionMimetypeMap.put("pbm", "image/x-portable-bitmap");
		extentionMimetypeMap.put("pdb", "chemical/x-pdb");
		extentionMimetypeMap.put("pdf", "application/pdf");
		extentionMimetypeMap.put("pgm", "image/x-portable-graymap");
		extentionMimetypeMap.put("pgn", "application/x-chess-pgn");
		extentionMimetypeMap.put("png", "image/png");
		extentionMimetypeMap.put("pnm", "image/x-portable-anymap");
		extentionMimetypeMap.put("ppm", "image/x-portable-pixmap");
		extentionMimetypeMap.put("ppt", "application/vnd.ms-powerpoint");
		extentionMimetypeMap.put("ps", "application/postscript");
		extentionMimetypeMap.put("qt", "video/quicktime");
		extentionMimetypeMap.put("ra", "audio/x-realaudio");
		extentionMimetypeMap.put("ram", "audio/x-pn-realaudio");
		extentionMimetypeMap.put("ras", "image/x-cmu-raster");
		extentionMimetypeMap.put("rgb", "image/x-rgb");
		extentionMimetypeMap.put("rm", "audio/x-pn-realaudio");
		extentionMimetypeMap.put("roff", "application/x-troff");
		extentionMimetypeMap.put("rpm", "#audio/x-pn-realaudio-plugin");
		extentionMimetypeMap.put("rpm", "application/x-rpm");
		extentionMimetypeMap.put("rtf", "application/rtf");
		extentionMimetypeMap.put("rtf", "text/rtf");
		extentionMimetypeMap.put("rtx", "text/richtext");
		extentionMimetypeMap.put("sgm", "text/sgml");
		extentionMimetypeMap.put("sgml", "text/sgml");
		extentionMimetypeMap.put("sh", "application/x-sh");
		extentionMimetypeMap.put("shar", "application/x-shar");
		extentionMimetypeMap.put("silo", "model/mesh");
		extentionMimetypeMap.put("sit", "application/x-stuffit");
		extentionMimetypeMap.put("skd", "application/x-koan");
		extentionMimetypeMap.put("skm", "application/x-koan");
		extentionMimetypeMap.put("skp", "application/x-koan");
		extentionMimetypeMap.put("skt", "application/x-koan");
		extentionMimetypeMap.put("smi", "application/smil");
		extentionMimetypeMap.put("smil", "application/smil");
		extentionMimetypeMap.put("snd", "audio/basic");
		extentionMimetypeMap.put("so", "application/octet-stream");
		extentionMimetypeMap.put("spl", "application/x-futuresplash");
		extentionMimetypeMap.put("src", "application/x-wais-source");
		extentionMimetypeMap.put("stc", "application/vnd.sun.xml.calc.template");
		extentionMimetypeMap.put("std", "application/vnd.sun.xml.draw.template");
		extentionMimetypeMap.put("sti", "application/vnd.sun.xml.impress.template");
		extentionMimetypeMap.put("stw", "application/vnd.sun.xml.writer.template");
		extentionMimetypeMap.put("sv4cpio", "application/x-sv4cpio");
		extentionMimetypeMap.put("sv4crc", "application/x-sv4crc");
		extentionMimetypeMap.put("swf", "application/x-shockwave-flash");
		extentionMimetypeMap.put("sxc", "application/vnd.sun.xml.calc");
		extentionMimetypeMap.put("sxd", "application/vnd.sun.xml.draw");
		extentionMimetypeMap.put("sxg", "application/vnd.sun.xml.writer.global");
		extentionMimetypeMap.put("sxi", "application/vnd.sun.xml.impress");
		extentionMimetypeMap.put("sxm", "application/vnd.sun.xml.math");
		extentionMimetypeMap.put("sxw", "application/vnd.sun.xml.writer");
		extentionMimetypeMap.put("t", "application/x-troff");
		extentionMimetypeMap.put("tar", "application/x-tar");
		extentionMimetypeMap.put("tcl", "application/x-tcl");
		extentionMimetypeMap.put("tex", "application/x-tex");
		extentionMimetypeMap.put("texi", "application/x-texinfo");
		extentionMimetypeMap.put("texinfo", "application/x-texinfo");
		extentionMimetypeMap.put("tgz", "application/x-gzip");
		extentionMimetypeMap.put("tif", "image/tiff");
		extentionMimetypeMap.put("tiff", "image/tiff");
		extentionMimetypeMap.put("tr", "application/x-troff");
		extentionMimetypeMap.put("tsv", "text/tab-separated-values");
		extentionMimetypeMap.put("txt", "text/plain");
		extentionMimetypeMap.put("ustar", "application/x-ustar");
		extentionMimetypeMap.put("vcd", "application/x-cdlink");
		extentionMimetypeMap.put("vrml", "model/vrml");
		extentionMimetypeMap.put("wav", "audio/x-wav");
		extentionMimetypeMap.put("wbmp", "image/vnd.wap.wbmp");
		extentionMimetypeMap.put("wbxml", "application/vnd.wap.wbxml");
		extentionMimetypeMap.put("wml", "text/vnd.wap.wml");
		extentionMimetypeMap.put("wmlc", "application/vnd.wap.wmlc");
		extentionMimetypeMap.put("wmls", "text/vnd.wap.wmlscript");
		extentionMimetypeMap.put("wmlsc", "application/vnd.wap.wmlscriptc");
		extentionMimetypeMap.put("wrl", "model/vrml");
		extentionMimetypeMap.put("xbm", "image/x-xbitmap");
		extentionMimetypeMap.put("xht", "application/xhtml+xml");
		extentionMimetypeMap.put("xhtml", "application/xhtml+xml");
		extentionMimetypeMap.put("xls", "application/vnd.ms-excel");
		extentionMimetypeMap.put("xml", "text/xml");
		extentionMimetypeMap.put("xpm", "image/x-xpixmap");
		extentionMimetypeMap.put("xsl", "text/xml");
		extentionMimetypeMap.put("xwd", "image/x-xwindowdump");
		extentionMimetypeMap.put("xyz", "chemical/x-xyz");
		extentionMimetypeMap.put("zip", "application/zip");
	}

	public static File write(String filePath, InputStream in) throws IOException {
		File file = new File(filePath);
		FileOutputStream out = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int readed = in.read(buffer);
		while (readed > 0) {
			out.write(buffer, 0, readed);
			readed = in.read(buffer);
		}
		in.close();
		out.close();
		return file;
	}

	public static String getMimeFromExt(String ext) {
		if (null == ext) {
			return "application/octet-stream";
		}
		String mime = extentionMimetypeMap.get(ext);
		if (null == mime) {
			return "application/octet-stream";
		} else {
			return mime;
		}
	}

	public static String getMimeType(File file) {
		return getMimeFromExt(getExt(file));
	}

	public static String getMimeType(String fileName) {
		return getMimeFromExt(getExt(fileName));
	}

	public static String getExt(File file) {
		return getExt(file.getName());
	}

	public static String getExt(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex < 0) {
			return null;
		}
		return fileName.substring(lastDotIndex + 1);
	}

	public static String getMimeTypeFromFileName(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex < 0) {
			return "application/octet-stream";
		}
		return getMimeFromExt(fileName.substring(lastDotIndex + 1));
	}

	public static String pathToName(String path) {
		int lastSepLength = path.lastIndexOf("\\");
		if (lastSepLength > 0) {
			return path.substring(lastSepLength + 1);
		}
		lastSepLength = path.lastIndexOf("/");
		if (lastSepLength > 0) {
			return path.substring(lastSepLength + 1);
		}
		return path;
	}

	public static String cleanDuplicatePath(String uri) {
		StringTokenizer token = new StringTokenizer(uri, "/");
		StringBuilder builder = new StringBuilder();
		while (token.hasMoreElements()) {
			String part = (String) token.nextElement();
			builder.append("/");
			builder.append(part);
		}
		return builder.toString();
	}
}
