package traviata.testreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import traviata.base.DateService;
import traviata.base.xml.XMLElement;

public class TbTestcase implements Serializable {
	private final List<TbAction> actions = new ArrayList<TbAction>();
	private final Map<String, String> vars = new TreeMap<String, String>();
	private String script;
	private String title;
	private int currentNumber; // lfd.Nr. (Command Nr.)
	private int runNumber;     // Lauf (lfd.Nr. innerhalb ExcelRow)
	private int size;          // Anzahl (Wert aus Spalte A der ExcelRow, ABZUG-bereinigt)
	private String scriptname;
	private String classification; // Produktname
	private java.util.Date starttime;
	private java.util.Date endtime;
	
	public TbTestcase() {
	}
	
	public TbTestcase(XMLElement e) {
		title = e.getValue("title");
		currentNumber = TbService.toInt(e, "currentNumber");
		runNumber = TbService.toInt(e, "runNumber");
		size = TbService.toInt(e, "size");
		scriptname = e.getValue("scriptname");
		classification = e.getValue("classification");
		starttime = TbService.toDate(e, "starttime");
		endtime = TbService.toDate(e, "endtime");
		for (XMLElement v : e.selectNodes("vars/var")) {
			vars.put(v.getValue("name"), v.getText());
		}
		script = "";
		for (XMLElement z : e.selectNodes("script/line")) {
			String tab = z.getValue("tab");
			if ("2".equals(tab)) {
				script += "\t\t";
			} else if ("1".equals(tab)) {
				script += "\t";
			}
			script += z.getText() + "\n";
		}
		for (XMLElement a : e.selectNodes("actions/*")) {
			actions.add(new TbAction(a));
		}
	}
	
	public void appendTo(XMLElement parent) {
		XMLElement et = parent.add("Testcase");
		et.setValue("title", title);
		et.setValue("currentNumber", "" + currentNumber);
		et.setValue("runNumber", "" + runNumber);
		et.setValue("size", "" + size);
		et.setValue("scriptname", scriptname);
		et.setValue("classification", classification);
		if (starttime != null) et.setValue("starttime", DateService.formatDateTime(starttime));
		if (endtime != null) et.setValue("endtime", DateService.formatDateTime(endtime));
		
		XMLElement var0 = et.add("vars");
		for (Map.Entry<String, String> ev : vars.entrySet()) {
			XMLElement var = var0.add("var");
			var.setValue("name", ev.getKey());
			var.setText(ev.getValue());
		}

		XMLElement ez0 = et.add("script");
		if (script != null) {
			for (String z : script.replace("\r", "").split("\n")) {
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
		}
		
		XMLElement p = et.add("actions");
		for (TbAction action : actions) {
			action.appendTo(p);
		}
	}

	/**
	 * @return meist Geschaeftsprozesse
	 */
	public List<TbAction> getActions() {
		return actions;
	}
	
	/**
	 * @return last TbErrorMessage
	 */
	public TbErrorMessage getErrorMessage() {
		TbErrorMessage f = null;
		for (TbAction a : getActions()) {
			for (TbActionItem i : a.getItems()) {
				if (i instanceof TbErrorMessage) {
					f = (TbErrorMessage) i;
				}
			}
		}
		return f;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	public int getRunNumber() {
		return runNumber;
	}

	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getScriptname() {
		return scriptname;
	}

	public void setScriptname(String scriptname) {
		this.scriptname = scriptname;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public java.util.Date getStarttime() {
		return starttime;
	}

	public void setStarttime(java.util.Date starttime) {
		this.starttime = starttime;
	}

	public java.util.Date getEndtime() {
		return endtime;
	}

	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}

	public Map<String, String> getVars() {
		return vars;
	}
	
	public TbAction getLastAction() {
		return getActions().get(getActions().size() - 1);
	}
	
	public void addError(String errorMessage) {
		if (errorMessage == null || errorMessage.isEmpty()) return;
		TbAction action;
		if (actions.isEmpty()) {
			actions.add(action = TbAction.empty());
		} else {
			action = getLastAction();
		}
		action.addError(errorMessage);
	}
}
