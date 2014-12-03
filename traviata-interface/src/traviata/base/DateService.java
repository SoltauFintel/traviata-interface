package traviata.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * java.util.Date = Date and time
 */
public class DateService {
	private static final DateFormat date = DateFormat.getDateInstance();
	private static final DateFormat date_time = DateFormat.getDateTimeInstance();
	
	private DateService() {
	}
	
	public static java.util.Date timeToDate(String pTime) {
		if (pTime == null || pTime.isEmpty()) {
			return null;
		}
		String t1 = format(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		return toDate(t1 + " "  + pTime);
	}
	
	public static String format(java.util.Date pDate) {
		return date.format(pDate);
	}
	
	public static java.util.Date toDate(String pDate) {
		try {
			return new java.util.Date(date_time.parse(pDate).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String formatDateTime(java.util.Date pDateTime) {
		return date_time.format(pDateTime);
	}
}
