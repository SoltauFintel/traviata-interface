package traviata.testbericht;

import java.io.Serializable;

import traviata.base.xml.XMLElement;
import traviata.testbericht.TbZeile;

public class TbZeile implements TbActionItem, Serializable {
	private String label;
	private String text;
	
	public TbZeile() {
	}

	public TbZeile(String label, String text) {
		this.label = label;
		this.text = text;
	}

	public TbZeile(XMLElement e) {
		label = e.getValue("label");
		text = e.getText();
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLabel() {
		return label;
	}

	public String getLabeld() {
		return label.trim().endsWith(":") ? label : label + ":";
	}

	public String getText() {
		return text;
	}

	@Override
	public void appendTo(XMLElement parent) {
		XMLElement e = parent.add("Zeile");
		e.setValue("label", label);
		e.setText(text);
	}
}
