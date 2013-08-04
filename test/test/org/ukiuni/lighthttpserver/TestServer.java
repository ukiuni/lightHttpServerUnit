package test.org.ukiuni.lighthttpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.ukiuni.lighthttpserver.HttpServer;
import org.ukiuni.lighthttpserver.request.Handler;
import org.ukiuni.lighthttpserver.request.Request;
import org.ukiuni.lighthttpserver.response.Response;
import org.ukiuni.lighthttpserver.util.StreamUtil;

public class TestServer {
	@Test
	public void testBasic() throws Exception {
		HttpServer server = new HttpServer(1080);
		server.getDefaultHandler().addResponse("/", "<html><body>テスト</body><html>").addResponse("/json", "{\"request\":\"success\"}", "application/json").addResponse("/text", "value", "text", "Shift_JIS");
		server.start();
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080").openConnection();
			Assert.assertEquals("<html><body>テスト</body><html>", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("text/html; charset=UTF-8", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/json").openConnection();
			Assert.assertEquals("{\"request\":\"success\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/json", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/text").openConnection();
			Assert.assertEquals("value", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("text", connection.getHeaderField("Content-Type"));
		}
		server.stop();
	}

	@Test
	public void testResponse() throws Exception {
		HttpServer server = new HttpServer(1080);
		final int responseCode = 200;
		final String contentType = "application/json";
		final String responseValue = "{\"result\":\"success\"}";
		final String responseEncode = "UTF-8";
		server.setHandler(new Handler() {
			@Override
			public Response onRequest(Request request) {
				request.getPath();
				return new Response() {
					@Override
					public void onResponse(OutputStream out) {
						try {
							write(out, responseCode, contentType, responseValue, responseEncode);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
			}

			@Override
			public void onException(Throwable e) {
				e.printStackTrace();
			}
		});
		server.start();
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080").openConnection();
		Assert.assertEquals(responseValue, StreamUtil.streamToString(connection.getInputStream(), responseEncode));
		Map<String, List<String>> headers = connection.getHeaderFields();
		for (String key : headers.keySet()) {
			List<String> values = headers.get(key);
			String value = "";
			for (String valueSrc : values) {
				value += valueSrc;
			}
			if ("Content-Length".equals(key)) {
				Assert.assertEquals(String.valueOf(responseValue.getBytes(responseEncode).length), value);
			} else if ("Content-Type".equals(key)) {
				Assert.assertEquals("application/json", value);
			}
		}
		server.stop();
	}
}
