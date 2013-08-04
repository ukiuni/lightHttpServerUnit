package org.ukiuni.lighthttpserver.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtil {
	public static String getUTC() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(cal.getTimeZone());
		String timestamp = df.format(cal.getTime());
		return timestamp;
	}
}
