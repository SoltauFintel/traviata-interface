package traviata.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * java.util.Date = Datum und Uhrzeit
 */
public class DateService {

	private DateService() {
	}
	
	public static java.util.Date uhrzeitToDate(String uhrzeit) {
		if (uhrzeit == null || uhrzeit.isEmpty()) {
			return null;
		}
		String t1 = format(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		return toDate(t1 + " "  + uhrzeit);
	}
	
	public static String format(java.util.Date pDatum) {
		Calendar c = Calendar.getInstance();
		c.setTime(pDatum);
		return zweistellig(c.get(Calendar.DAY_OF_MONTH)) + "." +
			zweistellig(c.get(Calendar.MONTH) + 1) + "." +
			c.get(Calendar.YEAR);
	}
	
	public static java.util.Date toDate(String pDatum) {
		DateFormat df = DateFormat.getDateTimeInstance();
		try {
			return new java.util.Date(df.parse(pDatum).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String zweistellig(int g) {
		if (g < 10) {
			return "0" + g;
		} else {
			return "" + g;
		}
	}
}
