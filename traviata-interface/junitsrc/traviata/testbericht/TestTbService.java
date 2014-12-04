package traviata.testbericht;

import org.junit.Assert;
import org.junit.Test;

import traviata.base.xml.XMLDocument;
import traviata.testreport.TbService;

public class TestTbService {

	@Test
	public void code6() {
		Assert.assertEquals("MMPL9L", TbService.code6("The quick brown fox jumps over the lazy dog."));
	}
	
	@Test
	public void toInt() {
		XMLDocument dok = new XMLDocument("<a><b x=\"79\"/></a>");
		Assert.assertEquals(79, TbService.toInt(dok.getChildren().get(0), "x"));
	}
}
