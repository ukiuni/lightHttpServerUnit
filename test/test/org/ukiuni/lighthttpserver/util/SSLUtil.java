package test.org.ukiuni.lighthttpserver.util;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLUtil {
	public static void toNoCheckSSLConnection(HttpsURLConnection connection) throws Exception{
		connection.setSSLSocketFactory(createNoCheckSSLFactory());
		connection.setHostnameVerifier(new NoCheckHostnameVerifier());
		connection.setDoOutput(true);
	}
	private static SSLSocketFactory createNoCheckSSLFactory() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new NoCheckX509TrustManager() };
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		return sc.getSocketFactory();
	}

	private static class NoCheckX509TrustManager implements X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	}

	private static class NoCheckHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
