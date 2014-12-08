package traviata.testreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.xml.XMLElement;

public class TbTable extends TbActionItemWithTitle implements Serializable {
	private List<String> headers;
	private List<List<String>> lines;

	public TbTable() {
	}

	public TbTable(XMLElement e) {
		setTitle(e.getValue("title"));
		headers = new ArrayList<String>();
		for (XMLElement c : e.selectNodes("header/c")) {
			headers.add(c.getText());
		}
		lines = new ArrayList<List<String>>();
		for (XMLElement z : e.selectNodes("row")) {
			List<String> spalten = new ArrayList<String>();
			for (XMLElement sp : z.getChildren()) {
				spalten.add(sp.getText());
			}
			lines.add(spalten);
		}
	}
	
	public static TbTable createTable(String title, String ... headers) {
		TbTable table = new TbTable();
		table.setTitle(title);
		table.setHeaders(makeList(headers));
		table.setLines(new ArrayList<List<String>>());
		return table;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<List<String>> getLines() {
		return lines;
	}

	public void setLines(List<List<String>> lines) {
		this.lines = lines;
	}

	@Override
	public void appendTo(XMLElement parent) {
		XMLElement tab = parent.add("Table");
		tab.setValue("title", getTitle());
		XMLElement h = tab.add("header");
		for (String u : headers) {
			h.add("c").setText(u);
		}
		for (List<String> zeile : lines) {
			XMLElement z = tab.add("row");
			for (String c : zeile) {
				z.add("c").setText(c);
			}
		}
	}
	
	public void addLine(String ... cols) {
		lines.add(makeList(cols));
	}
	
	private static List<String> makeList(String ... items) {
		List<String> list = new ArrayList<String>();
		for (String item : items) {
			list.add(item);
		}
		return list;
	}
}
