package org.ukiuni.lighthttpserver.request;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ParameterFile {
	protected String contentType;
	protected String fileName;
	protected byte[] content;

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.content);
	}

}