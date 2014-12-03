package traviata.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * java.util.Date = Date and time
 */
public class DateService {
	private static final DateFormat ddmmyyyy  = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat date_time = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
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
		return ddmmyyyy.format(date);
	}
	
	public static java.util.Date toDate(String date) {
		DateFormat df = DateFormat.getDateTimeInstance();
		try {
			return new java.util.Date(df.parse(date).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String formatDateTime(java.util.Date dateTime) {
		return date_time.format(dateTime);
	}
}
