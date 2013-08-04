package test.org.ukiuni.lighthttpserver.util;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.ukiuni.lighthttpserver.util.ByteReader;

public class TestByteReader {
	@Test
	public void testReadTo() throws Exception {
		ByteReader reader = new ByteReader(new ByteArrayInputStream("paramset=test\r\n".getBytes("UTF-8")), "UTF-8");
		Assert.assertEquals("paramset=test", new String(reader.readTo("\r\n".getBytes("UTF-8")), "UTF-8"));
		reader.close();
	}

	@Test
	public void testReadTo2() throws Exception {
		ByteReader reader = new ByteReader(new ByteArrayInputStream("paramset=test\r\nasdfasd".getBytes("UTF-8")), "UTF-8");
		Assert.assertEquals("paramset=test", new String(reader.readTo("\r\n".getBytes("UTF-8")), "UTF-8"));
		Assert.assertEquals("asdfasd", new String(reader.readTo("\r\n".getBytes("UTF-8")), "UTF-8"));
		reader.close();
	}
}
