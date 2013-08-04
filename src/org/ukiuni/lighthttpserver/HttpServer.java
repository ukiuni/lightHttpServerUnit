package org.ukiuni.lighthttpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.ukiuni.lighthttpserver.request.DefaultHandler;
import org.ukiuni.lighthttpserver.request.Handler;

public class HttpServer {
	private boolean started;
	private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		if (null != executorService) {
			executorService.shutdown();
			try {
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		this.executorService = executorService;
	}

	public Handler getHandler() {
		if (null == handler) {
			handler = new DefaultHandler();
		}
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isStarted() {
		return started;
	}

	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private Handler handler;

	public HttpServer() {
	}

	public HttpServer(int port) {
		this.port = port;
	}

	public HttpServer start() throws IOException {
		if (started) {
			throw new IllegalStateException("server aleady started");
		}
		started = true;
		serverSocket = new ServerSocket(port);
		if (null == executorService) {
			executorService = Executors.newSingleThreadExecutor();
		}
		if (null == handler) {
			handler = new DefaultHandler();
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (started) {
					try {
						Socket socket = serverSocket.accept();
						Client client = new Client(socket, handler);
						client.init();
						client.handleRequest();
						client.close();
					} catch (Exception e) {
						if (started && null != handler) {
							handler.onException(e);
						}
					}
				}
			}
		};
		executorService.execute(runnable);
		return this;
	}

	public void stop() throws IOException, InterruptedException {
		started = false;
		if (null != serverSocket) {
			serverSocket.close();
		}
		if (null != executorService) {
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		}
	}

	public DefaultHandler getDefaultHandler() {
		DefaultHandler handler = new DefaultHandler();
		this.handler = handler;
		return handler;
	}
}
