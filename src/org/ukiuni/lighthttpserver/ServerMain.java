package org.ukiuni.lighthttpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.ukiuni.lighthttpserver.request.ResponseStaticContentHandler;

public class ServerMain {
	public static void main(String[] args) throws IOException {
		int port = 10080;
		int managePort = 10081;
		boolean modeStop = false;
		String baseDirPath = null;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if ("-port".equals(arg)) {
				port = Integer.parseInt(args[i + 1]);
			} else if ("-managePort".equals(arg)) {
				managePort = Integer.parseInt(args[i + 1]);
			} else if ("-baseDir".endsWith(arg)) {
				baseDirPath = args[i + 1];
			} else if ("stop".equals(arg)) {
				modeStop = true;
			}
		}
		if (modeStop) {
			new Socket("localhost", managePort);
			return;
		}
		final HttpServer server = new HttpServer(port);
		server.setHandler(new ResponseStaticContentHandler(baseDirPath));
		server.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("Stop server....");
					server.stop();
					System.out.println("Server stoped");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("Server started. Ctrl + C with exit.");
		if (managePort > 0) {
			final ServerSocket socket = new ServerSocket(managePort);
			socket.accept();
			System.exit(0);
			System.out.println("or enter \"lightServer stop\"");
		}
	}
}
