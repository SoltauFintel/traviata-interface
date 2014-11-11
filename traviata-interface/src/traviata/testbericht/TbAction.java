package traviata.testbericht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import traviata.base.xml.XMLElement;

/**
 * Meist Geschäftsprozss
 */
public class TbAction implements Serializable {
	private String titel;
	private java.util.Date startzeit;
	private final List<TbActionItem> items = new ArrayList<TbActionItem>();
	
	public TbAction() {
	}
	
	public TbAction(XMLElement ea) {
		titel = ea.getValue("titel");
		startzeit = TbService.toDate(ea, "startzeit");
		for (XMLElement e : ea.getChildren()) {
			TbActionItem item;
			String n = e.getName();
			if ("Zeile".equals(n)) {
				item = new TbZeile(e);
			} else if ("Block".equals(n)) {
				item = new TbBlock(e);
			} else if ("Fehlermeldung".equals(n)) {
				item = new TbFehlermeldung(e);
			} else if ("Tabelle".equals(n)) {
				item = new TbTabelle(e);
			} else {
				throw new RuntimeException("Unbekannter Item: " + n);
			}
			items.add(item);
		}
	}
	
	public String getTitel() {
		return titel;
	}

	public Date getStartzeit() {
		return startzeit;
	}

	public List<TbActionItem> getItems() {
		return items;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public void setStartzeit(java.util.Date startzeit) {
		this.startzeit = startzeit;
	}
	
	public void appendTo(XMLElement parent) {
		XMLElement e = parent.add("Action");
		e.setValue("titel", titel);
		if (startzeit != null) e.setValue("startzeit", TbService.format(startzeit));
		for (TbActionItem item : items) {
			item.appendTo(e);
		}
	}
}
