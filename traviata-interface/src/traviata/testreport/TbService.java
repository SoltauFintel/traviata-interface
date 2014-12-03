package traviata.testreport;

import java.util.zip.CRC32;

import traviata.base.DateService;
import traviata.base.xml.XMLElement;

public class TbService {

	private TbService() {
	}

	public static String code6(String str) {
		CRC32 crc = new CRC32();
		crc.update(str.getBytes());
		String ret = "000000" + Integer.toString((int) crc.getValue(), 36).toUpperCase().replace("-", "");
		return ret.substring(ret.length() - 6);
	}

	public static int toInt(XMLElement e, String fieldname) {
		return Integer.parseInt(e.getValue(fieldname));
	}
	
	public static java.util.Date toDate(XMLElement e, String fieldname) {
		String val = e.getValue(fieldname);
		return val.isEmpty() ? null : DateService.toDate(val);
	}
}
