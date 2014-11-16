package traviata.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * java.util.Date = Date and time
 */
public class DateService {

	private DateService() {
	}
	
	public static java.util.Date timeToDate(String time) {
		if (time == null || time.isEmpty()) {
			return null;
		}
		String t1 = format(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		return toDate(t1 + " "  + time);
	}
	
	public static String format(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return twoDigits(c.get(Calendar.DAY_OF_MONTH)) + "." +
			twoDigits(c.get(Calendar.MONTH) + 1) + "." +
			c.get(Calendar.YEAR);
	}
	
	public static java.util.Date toDate(String date) {
		DateFormat df = DateFormat.getDateTimeInstance();
		try {
			return new java.util.Date(df.parse(date).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String twoDigits(int number) {
		if (number < 10) {
			return "0" + number;
		} else {
			return "" + number;
		}
	}
}
