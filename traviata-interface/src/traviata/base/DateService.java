package traviata.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * java.util.Date = Date and time
 */
public class DateService {
	private static final DateFormat date = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);
	private static final DateFormat date_time = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.GERMANY);
	/** yyyy-MM-dd */
	public static final SimpleDateFormat C_DATE = new SimpleDateFormat("yyyy-MM-dd");
	/** HH-mm */
	public static final SimpleDateFormat C_TIME = new SimpleDateFormat("HH-mm");

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

	public static String nowString() {
		return new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
	}
	
	public static String todayPlus(int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new java.util.Date());
		c.add(Calendar.DAY_OF_MONTH, days);
		return date.format(c.getTime());
	}

	/**
	 * @param hours
	 * @return java.util.Date hours ago
	 */
	public static java.util.Date hoursAgo(int hours) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, -hours);
		return c.getTime();
	}

	/**
	 * @param days
	 * @return java.util.Date days ago
	 */
	public static java.util.Date daysAgo(int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -days);
		return c.getTime();
	}
}
