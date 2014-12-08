package traviata.base.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * XMLElement implementation
 */
public class XMLElementImpl implements XMLElement {
	private final Element element;

	/**
	 * @param pElement
	 */
	XMLElementImpl(Element pElement) { 
		element = pElement;
	}
	
	@Override
	public String getName() {
		return element.getName();
	}
	
	@Override
	public void setName(String name) {
		element.setName(name);
	}

	@Override
	public String getValue(String attributname) {
		String ret = element.attributeValue(attributname);
		return ret == null ? "" : ret;
	}
	
	@Override
	public String getMultiLineValue(String attributname) {
		return getValue(attributname).replace(NEWLINE, "\n");
	}
	
	@Override
	public void setValue(String attributname, String value) {
		element.addAttribute(attributname, value);
	}
	
	@Override
	public void setMultiLineValue(String attributname, String value) {
		if (value == null) {
			setValue(attributname, value);
		} else {
			setValue(attributname, 
					value.replace("\r", "").replace("\n", NEWLINE));
		}
	}
	
	@Override
	public void setValueIfNotNull(String attributname, String value) {
		if (value != null) {
			element.addAttribute(attributname, value);
		}
	}
	
	@Override
	public List<XMLElement> getChildren() {
		return getChildElements(element.elements());
	}
	
	@Override
	public int getChildrenCount() {
		return element.elements().size();
	}
	
	@Override
	public boolean hasChildren() {
		return element.elements().size() > 0;
	}
	
	protected static List<XMLElement> getChildElements(List<?> list) {
		List<XMLElement> ret = new ArrayList<XMLElement>();
		for(Object e : list) {
			ret.add(create((Element) e));
		}
		return ret;
	}
	
	@Override
	public String getXML() {
		return element.asXML();
	}
	
	@Override
	public List<XMLElement> selectNodes(String xpath) {
		return getChildElements(element.selectNodes(xpath));
	}
	
	@Override
	public XMLElement selectSingleNode(String xpath) {
		Node node = element.selectSingleNode(xpath);
		if (node == null || !(node instanceof Element)) {
			return null;
		} else {
			return create((Element) node);
		}
	}

	@Override
	public int getAttributeCount() {
		return element.attributeCount();
	}
	
	@Override
	public String getAttributeName(int index) {
		return element.attribute(index).getName();
	}
	
	@Override
	public String getText() {
		return element.getText();
	}

	@Override
	public void setText(String text) {
		element.setText(text);
	}
	
	@Override
	public XMLElement add(String elementName) {
		return create(element.addElement(elementName));
	}
	
	@Override
	public List<String> getArray(String pAttributName) {
		List<String> array = new ArrayList<String>();
		for (XMLElement e : getChildren()) {
			array.add(e.getValue(pAttributName));
		}
		return array;
	}
	
	@Override
	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < element.attributeCount(); i++) {
			Attribute attr = element.attribute(i);
			map.put(attr.getName(), attr.getValue());
		}
		return map;
	}
	
	@Override
	public void append(String xml) {
		try {
			Document dok = DocumentHelper.parseText(xml);
			element.add(dok.getRootElement());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void removeChildren(String elementName) {
		List<?> list = element.selectNodes(elementName);
		for (Iterator<?> iter = list.iterator(); iter.hasNext();) {
			element.remove((Element) iter.next());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public XMLElement insertBefore(int beforeIndex, String newElementName) {
		Element newElement = new DocumentFactory().createElement(newElementName);
		int newIndex = -1;
		List<?> c = element.content();
		for (int i = 0; i < c.size(); i++) {
			String n = c.get(i).getClass().getName();
			if (n.endsWith("Element")) {
				newIndex++;
				if (newIndex == beforeIndex) {
					beforeIndex = i;
					break;
				}
			}
		}
		element.content().add(beforeIndex, newElement);
		return create(newElement);
	}

	@Override
	public int indexByName(String elementName, int start) {
		List<?> children = element.elements();
		for (int i = start; i < children.size(); i++) {
			if (((Element) children.get(i)).getName().equals(elementName)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void removeAttribute(String elementName) {
		try {
			element.remove(element.attribute(elementName));
		} catch (Exception ignored) { 
		}
	}
	
	protected static XMLElement create(Element pElement) {
		return new XMLElementImpl(pElement);
	}
	
	@Override
	public boolean hasAttribute(String attributname) {
		return element.attributeValue(attributname) != null;
	}

	@Override
	public void removeEmptyAttributes() {
		List<String> kill = new ArrayList<String>();
		for (int i = 0; i < getAttributeCount(); i++) {
			String name = getAttributeName(i);
			if ("".equals(getValue(name))) {
				kill.add(name);
			}
		}
		for (String name : kill) {
			removeAttribute(name);
		}
	}
}
