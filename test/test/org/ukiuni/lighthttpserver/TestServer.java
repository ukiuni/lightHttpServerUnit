package test.org.ukiuni.lighthttpserver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ukiuni.lighthttpserver.HttpServer;
import org.ukiuni.lighthttpserver.request.DefaultHandler;
import org.ukiuni.lighthttpserver.request.Handler;
import org.ukiuni.lighthttpserver.request.Request;
import org.ukiuni.lighthttpserver.response.Response;
import org.ukiuni.lighthttpserver.util.StreamUtil;

import test.org.ukiuni.lighthttpserver.util.SSLUtil;

public class TestServer {
	HttpServer server;

	@Before
	public void setupServer() throws IOException {
		server = new HttpServer(1080);
		server.start();
	}

	@After
	public void stopServer() throws IOException, InterruptedException {
		if (null != server) {
			server.stop();
		}
	}

	@Test
	public void testBasic() throws Exception {
		server.getDefaultHandler().addResponse("/", "<html><body>テスト</body><html>").addResponse("/json", "{\"request\":\"success\"}", "application/json").addResponse("/text", "value", "text", "Shift_JIS");
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
	}

	@Test
	public void testResponse() throws Exception {
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
							write(out, responseCode, responseValue, contentType, responseEncode);
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
	}

	@Test
	public void testPost() throws Exception {
		server.getDefaultHandler().addResponse("/json", "{\"request\":\"success\"}", "application/json");
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/json").openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			OutputStream requestOut = connection.getOutputStream();
			requestOut.write("param=isParam\n".getBytes());
			Assert.assertEquals("{\"request\":\"success\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/json", connection.getHeaderField("Content-Type"));
		}
	}
	
	@Test
	public void testSSLPost() throws Exception {
		server.stop();
	//	server = new HttpServer(1080);
		server.setSsl(true);
		server.start();
		//Thread.sleep(1000);
		server.getDefaultHandler().addResponse("/json", "{\"request\":\"success\"}", "application/json");
		{
			HttpsURLConnection connection = (HttpsURLConnection) new URL("https://localhost:1080/json").openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			SSLUtil.toNoCheckSSLConnection(connection);
			OutputStream requestOut = connection.getOutputStream();
			requestOut.write("param=isParam\n".getBytes());
			Assert.assertEquals("{\"request\":\"success\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/json", connection.getHeaderField("Content-Type"));
		}
	}

	@Test
	public void testDefaultHandler() throws Exception {
		DefaultHandler handler = server.getDefaultHandler();
		handler.addResponse("/simple", "simple value");
		handler.addResponse("/json", "{\"request\":\"success1\"}", "application/json");
		handler.addResponse("/json201", 201, "{\"request\":\"success2\"}");
		handler.addResponse("/jsonUTF", 203, "{\"request\":\"success3\"}", "application/json", "UTF-8");
		handler.addResponse("/file1", new File("test/testfile1.html"));
		handler.addResponse("/file2", 201, new File("test/testfile2"));
		handler.addResponse("/file3", 202, new File("test/testfile3"), "application/json");
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/simple").openConnection();
			Assert.assertEquals(200, connection.getResponseCode());
			Assert.assertEquals("simple value", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("text/html; charset=UTF-8", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/json").openConnection();
			Assert.assertEquals(200, connection.getResponseCode());
			Assert.assertEquals("{\"request\":\"success1\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/json", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/json201").openConnection();
			Assert.assertEquals(201, connection.getResponseCode());
			Assert.assertEquals("{\"request\":\"success2\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("text/html; charset=UTF-8", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/jsonUTF").openConnection();
			Assert.assertEquals(203, connection.getResponseCode());
			Assert.assertEquals("{\"request\":\"success3\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/json", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/file1").openConnection();
			Assert.assertEquals(200, connection.getResponseCode());
			Assert.assertEquals("testfile1 is sended.", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("text/html", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/file2").openConnection();
			Assert.assertEquals(201, connection.getResponseCode());
			Assert.assertEquals("testfile2 is sended.", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/octet-stream", connection.getHeaderField("Content-Type"));
		}
		{
			HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:1080/file3").openConnection();
			Assert.assertEquals(202, connection.getResponseCode());
			Assert.assertEquals("{\"arg\":\"testfile3\"}", StreamUtil.streamToString(connection.getInputStream(), "UTF-8"));
			Assert.assertEquals("application/json", connection.getHeaderField("Content-Type"));
		}
	}
}
