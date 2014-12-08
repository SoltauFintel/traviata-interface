package traviata.testreport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import traviata.base.xml.XMLElement;

/**
 * Compact display of lines, with title
 */
public class TbBlock extends TbActionItemWithTitle implements Serializable {
	private final List<TbLine> lines = new ArrayList<TbLine>();
	
	public TbBlock() {
	}
	
	public TbBlock(XMLElement e) {
		if (e.hasAttribute("title")) {
			setTitle(e.getValue("title"));
		}
		for (XMLElement ee : e.getChildren()) {
			lines.add(new TbLine(ee));
		}
	}

	public List<TbLine> getLines() {
		return lines;
	}

	public void add(String label, String text) {
		lines.add(new TbLine(label, text));
	}
	
	@Override
	public void appendTo(XMLElement parent) {
		XMLElement e = parent.add("Block");
		if (getTitle() != null) {
			e.setValue("title", getTitle());
		}
		for (TbLine z : lines) {
			z.appendTo(e);
		}
	}
}
