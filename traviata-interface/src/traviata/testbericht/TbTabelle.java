package traviata.testbericht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.xml.XMLElement;

public class TbTabelle extends TbActionItemMitTitel implements Serializable {
	private List<String> ueberschriften;
	private List<List<String>> zeilen;

	public TbTabelle() {
	}

	public TbTabelle(XMLElement e) {
		setTitel(e.getValue("titel"));
		ueberschriften = new ArrayList<String>();
		for (XMLElement c : e.selectNodes("header/c")) {
			ueberschriften.add(c.getText());
		}
		zeilen = new ArrayList<List<String>>();
		for (XMLElement z : e.selectNodes("row")) {
			List<String> spalten = new ArrayList<String>();
			for (XMLElement sp : z.getChildren()) {
				spalten.add(sp.getText());
			}
			zeilen.add(spalten);
		}
	}
	
	public void setUeberschriften(List<String> ueberschriften) {
		this.ueberschriften = ueberschriften;
	}

	public void setZeilen(List<List<String>> zeilen) {
		this.zeilen = zeilen;
	}

	public List<String> getUeberschriften() {
		return ueberschriften;
	}

	public List<List<String>> getZeilen() {
		return zeilen;
	}

	@Override
	public void appendTo(XMLElement parent) {
		XMLElement tab = parent.add("Tabelle");
		tab.setValue("titel", getTitel());
		XMLElement h = tab.add("header");
		for (String u : ueberschriften) {
			h.add("c").setText(u);
		}
		for (List<String> zeile : zeilen) {
			XMLElement z = tab.add("row");
			for (String c : zeile) {
				z.add("c").setText(c);
			}
		}
	}
}
