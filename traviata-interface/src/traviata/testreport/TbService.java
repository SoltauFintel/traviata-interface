package traviata.testreport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.zip.CRC32;

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

	public static int toInt(XMLElement e, String feldname) {
		return Integer.parseInt(e.getValue(feldname));
	}
	
	public static java.util.Date toDate(XMLElement e, String feldname) {
		String i = e.getValue(feldname);
		if (i.isEmpty()) {
			return null;
		}
		try {
			return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(i);
		} catch (ParseException e1) {
			throw new RuntimeException("Error parsing: " + i, e1);
		}
	}
}
