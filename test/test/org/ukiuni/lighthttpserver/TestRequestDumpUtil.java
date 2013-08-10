package test.org.ukiuni.lighthttpserver;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ukiuni.lighthttpserver.HttpServer;
import org.ukiuni.lighthttpserver.RequestDumpUtil;
import org.ukiuni.lighthttpserver.util.StreamUtil;

public class TestRequestDumpUtil {
	HttpServer server;

	@Before
	public void setupServer() throws IOException {
		server = new HttpServer(8000);
		server.start();
	}

	@After
	public void stopServer() throws IOException, InterruptedException {
		if (null != server) {
			server.stop();
		}
	}

	@Test
	public void testDump() throws IOException {
		server.getDefaultHandler().addResponse("/test/test/neme.dest", "result data");
		RequestDumpUtil.dump("http://localhost:8000/test/test/neme.dest?degen=bar&zende=pa");
		File destFile = new File("test/test/neme.dest");
		String registed = StreamUtil.fileToString(destFile, "UTF-8");
		destFile.delete();
		Assert.assertEquals("result data", registed);
	}

	@Test
	public void testDumpPost() throws IOException {
		server.getDefaultHandler().requestLoggingOn();
		server.getDefaultHandler().addResponse("/test/test/neme.dest2", "result data");
		RequestDumpUtil.dump("http://localhost:8000/test/test/neme.dest2?degen=bar&zende=pa", "POST", null, null, null);
		File destFile = new File("test/test/neme.dest2");
		String registed = StreamUtil.fileToString(destFile, "UTF-8");
		destFile.delete();
		Assert.assertEquals("result data", registed);
		Assert.assertEquals("bar", server.getDefaultHandler().getRequestLog().get(0).getParameter("degen"));
		Assert.assertEquals("pa", server.getDefaultHandler().getRequestLog().get(0).getParameter("zende"));
		server.getDefaultHandler().requestLoggingOff();
	}
}
