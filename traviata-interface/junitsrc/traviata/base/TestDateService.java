package traviata.base;

import org.junit.Assert;
import org.junit.Test;

public class TestDateService {

//	@Test
	public void toDate() {
		Assert.assertEquals(1420066243000l, DateService.toDate("31.12.2014 23:50:43").getTime());
	}
	
	@Test(expected = RuntimeException.class)
	public void toDate_fail() {
		DateService.toDate("Quatsch");
	}
	
	@Test
	public void format() {
		final String f = "13.12.2014";
		Assert.assertEquals(f, DateService.format(DateService.toDate(f + " 00:00:00")));
	}
	
	@Test
	public void formatDateTime() {
		final String f = "13.12.2014 15:00:17";
		java.util.Date d = DateService.toDate(f);
		Assert.assertEquals(f, DateService.formatDateTime(d));
	}
}
