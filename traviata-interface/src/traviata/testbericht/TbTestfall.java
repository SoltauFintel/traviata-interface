package traviata.testbericht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import traviata.base.xml.XMLElement;

public class TbTestfall implements Serializable {
	private final List<TbAction> actions = new ArrayList<TbAction>();
	private final Map<String, String> vars = new TreeMap<String, String>();
	private String VTScript;
	private String titel;
	private int lfdNr, lauf, anzahl;
	private String dateinameVTScript;
	private java.util.Date startzeit;
	private java.util.Date endezeit;
	
	public TbTestfall() {
	}
	
	public TbTestfall(XMLElement e) {
		titel = e.getValue("titel");
		lfdNr = TbService.toInt(e, "lfdNr");
		lauf = TbService.toInt(e, "lauf");
		anzahl = TbService.toInt(e, "anzahl");
		dateinameVTScript = e.getValue("dn");
		startzeit = TbService.toDate(e, "startzeit");
		endezeit = TbService.toDate(e, "endezeit");
		for (XMLElement v : e.selectNodes("vars/var")) {
			vars.put(v.getValue("name"), v.getText());
		}
		VTScript = "";
		for (XMLElement z : e.selectNodes("vtscript/line")) {
			String tab = z.getValue("tab");
			if ("2".equals(tab)) {
				VTScript += "\t\t";
			} else if ("1".equals(tab)) {
				VTScript += "\t";
			}
			VTScript += z.getText() + "\n";
		}
		for (XMLElement a : e.selectNodes("actions/*")) {
			actions.add(new TbAction(a));
		}
	}
	
	public void appendTo(XMLElement parent) {
		XMLElement et = parent.add("Testfall");
		et.setValue("titel", titel);
		et.setValue("lfdNr", "" + lfdNr);
		et.setValue("lauf", "" + lauf);
		et.setValue("anzahl", "" + anzahl);
		et.setValue("dn", dateinameVTScript);
		if (startzeit != null) et.setValue("startzeit", TbService.format(startzeit));
		if (endezeit != null) et.setValue("endezeit", TbService.format(endezeit));
		
		XMLElement var0 = et.add("vars");
		for (Map.Entry<String, String> ev : vars.entrySet()) {
			XMLElement var = var0.add("var");
			var.setValue("name", ev.getKey());
			var.setText(ev.getValue());
		}

		XMLElement ez0 = et.add("vtscript");
		for (String z : VTScript.replace("\r", "").split("\n")) {
			XMLElement ez = ez0.add("line");
			if (z.startsWith("\t\t")) {
				ez.setValue("tab", "2");
				z = z.substring(2);
			} else if (z.startsWith("\t")) {
				ez.setValue("tab", "1");
				z = z.substring(1);
			} else {
				ez.setValue("tab", "0");
			}
			ez.setText(z);
		}
		
		XMLElement p = et.add("actions");
		for (TbAction action : actions) {
			action.appendTo(p);
		}
	}
	
	public void setEndezeit(java.util.Date endezeit) {
		this.endezeit = endezeit;
	}

	public void setStartzeit(java.util.Date startzeit) {
		this.startzeit = startzeit;
	}

	public void setDateinameVTScript(String dateinameVTScript) {
		this.dateinameVTScript = dateinameVTScript;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public void setLfdNr(int lfdNr) {
		this.lfdNr = lfdNr;
	}

	public void setLauf(int lauf) {
		this.lauf = lauf;
	}

	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	public void setVTScript(String vTScript) {
		VTScript = vTScript;
	}

	public String getId() {
		return TbService.code6(getVTScript());
	}

	public String getTitel() {
		return titel;
	}

	public String getVTScript() {
		return VTScript;
	}

	public Map<String, String> getVars() {
		return vars;
	}

	public String getProduktname() {
		return getVars().get("produkt");
	}

	public String getDateinameVTScript() {
		return dateinameVTScript;
	}

	public int getLfdNr() {
		return lfdNr;
	}

	public int getLauf() {
		return lauf;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public Date getStartzeit() {
		return startzeit;
	}

	public Date getEndezeit() {
		return endezeit;
	}

	/**
	 * @return meist Geschäftsprozesse
	 */
	public List<TbAction> getActions() {
		return actions;
	}
	
	/**
	 * @return letzte TbFehlermeldung
	 */
	public TbFehlermeldung getFehlermeldung() {
		TbFehlermeldung f = null;
		for (TbAction a : getActions()) {
			for (TbActionItem i : a.getItems()) {
				if (i instanceof TbFehlermeldung) {
					f = (TbFehlermeldung) i;
				}
			}
		}
		return f;
	}
}
