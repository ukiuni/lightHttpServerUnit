package org.ukiuni.lighthttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.ukiuni.lighthttpserver.request.Handler;
import org.ukiuni.lighthttpserver.request.Request;
import org.ukiuni.lighthttpserver.response.Response;
import org.ukiuni.lighthttpserver.util.StreamUtil;

public class Client {
	private Socket socket;
	private Handler handler;
	private InputStream requestInputStream;
	private OutputStream responseOutputStream;

	public Client(Socket socket, Handler handler) {
		this.socket = socket;
		this.handler = handler;
	}

	public void init() throws IOException {
		this.requestInputStream = this.socket.getInputStream();
		this.responseOutputStream = this.socket.getOutputStream();
	}

	public void handleRequest() throws IOException {
		Request request = parseRequest();
		Response response = handler.onRequest(request);
		try {
			response.onResponse(this.responseOutputStream);
		} catch (Throwable e) {
			if (null != handler) {
				handler.onException(e);
			}
		}
		this.close();
	}

	private Request parseRequest() throws IOException {
		return Request.parseInput(requestInputStream, handler.getRequestPaseEncode());
	}

	public InputStream getRequestInputStream() {
		return requestInputStream;
	}

	public OutputStream getResponseOutputStream() {
		return responseOutputStream;
	}

	public void close() throws IOException {
		StreamUtil.closeQuietry(requestInputStream);
		StreamUtil.closeQuietry(responseOutputStream);
		this.socket.close();
	}
}
