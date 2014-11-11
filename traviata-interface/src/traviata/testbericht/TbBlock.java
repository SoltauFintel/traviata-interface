package traviata.testbericht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.xml.XMLElement;

/**
 * Kompakte Darstellung von Zeilen, mit Titel
 */
public class TbBlock extends TbActionItemMitTitel implements Serializable {
	private final List<TbZeile> zeilen = new ArrayList<TbZeile>();
	
	public TbBlock() {
	}
	
	public TbBlock(XMLElement e) {
		setTitel(e.getValue("titel"));
		for (XMLElement ee : e.getChildren()) {
			zeilen.add(new TbZeile(ee));
		}
	}

	public List<TbZeile> getZeilen() {
		return zeilen;
	}

	public void add(String label, String text) {
		zeilen.add(new TbZeile(label, text));
	}
	
	@Override
	public void appendTo(XMLElement parent) {
		XMLElement e = parent.add("Block");
		e.setValue("titel", getTitel());
		for (TbZeile z : zeilen) {
			z.appendTo(e);
		}
	}
}
