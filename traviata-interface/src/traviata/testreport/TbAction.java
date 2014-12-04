package traviata.testreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.DateService;
import traviata.base.xml.XMLElement;

/**
 * An action is usually a business process.
 */
public class TbAction implements Serializable {
	private String title;
	private String starttime;
	private final List<TbActionItem> items = new ArrayList<TbActionItem>();
	
	public TbAction() {
	}
	
	public static TbAction empty() {
		TbAction a = new TbAction();
		a.setStarttime(DateService.nowString());
		a.setTitle("");
		return a;
	}
	
	public TbAction(XMLElement ea) {
		title = ea.getValue("title");
		starttime = ea.getValue("starttime");
		for (XMLElement e : ea.getChildren()) {
			TbActionItem item;
			String n = e.getName();
			if ("Line".equals(n)) {
				item = new TbLine(e);
			} else if ("Block".equals(n)) {
				item = new TbBlock(e);
			} else if ("ErrorMessage".equals(n)) {
				item = new TbErrorMessage(e);
			} else if ("Table".equals(n)) {
				item = new TbTable(e);
			} else {
				throw new RuntimeException("Unknown item: " + n);
			}
			items.add(item);
		}
	}
	
	public String getTitle() {
		return title;
	}

	public String getStarttime() {
		return starttime;
	}

	public List<TbActionItem> getItems() {
		return items;
	}
	
	public void add(TbActionItem item) {
		items.add(item);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	public void addError(String errorMessage) {
		items.add(new TbErrorMessage(errorMessage));
	}
	
	public void appendTo(XMLElement parent) {
		XMLElement e = parent.add("Action");
		e.setValue("title", title);
		if (starttime != null) e.setValue("starttime", starttime);
		for (TbActionItem item : items) {
			item.appendTo(e);
		}
	}
}
