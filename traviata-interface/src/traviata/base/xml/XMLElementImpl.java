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
 * XML Element
 * <br>XML Kapselung fuer vereinfachten DOM-basierten XML-Zugriff
 */
public class XMLElementImpl implements XMLElement {
	protected Element element;

	/**
	 * Konstruktor
	 * @param pElement
	 */
	XMLElementImpl(Element pElement) { 
		element = pElement;
	}
	
	/**
	 * @return Tag-Name des XML Elements
	 */
	@Override
	public String getName() {
		return element.getName();
	}
	
	@Override
	public void setName(String name) {
		element.setName(name);
	}
	
	/**
	 * Lesezugriff auf ein Attribut
	 * 
	 * @param pAttributname
	 * @return Attributinhalt als String oder "" wenn Attribut nicht vorhanden
	 */
	public String getValue(String pAttributname) {
		String ret = element.attributeValue(pAttributname);
		if (ret == null) {
			ret = "";
		}
		return ret;
	}
	
	@Override
	public String getMultiLineValue(String pAttributname) {
		String ret = getValue(pAttributname);
		return ret.replace(NEWLINE, "\n");
	}
	
	@Override
	public void setValue(String pAttributname, String pValue) {
		element.addAttribute(pAttributname, pValue);
	}
	
	@Override
	public void setMultiLineValue(String pAttributname, String pValue) {
		if (pValue == null) {
			setValue(pAttributname, pValue);
		} else {
			setValue(pAttributname, 
					pValue.replace("\r", "").replace("\n", NEWLINE));
		}
	}
	
	public void setValueIfNotNull(String pAttributname, String pValue) {
		if (pValue != null) {
			element.addAttribute(pAttributname, pValue);
		}
	}
	
	/**
	 * @return Kindelemente
	 */
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
	
	/**
	 * @return XML String
	 */
	@Override
	public String getXML() {
		return element.asXML();
	}
	
	/**
	 * Wenn die XPath-Anweisung fehlerhaft ist wird eine Exception ausgeloest.
	 * @param pXPath XPath String. Der XPath String muss so aufgebaut sein,
	 * 			                   dass nur Elemente zurueckgegeben werden.
	 * @return XMLElement Liste
	 */
	@Override
	public List<XMLElement> selectNodes(String pXPath) {
		return getChildElements(element.selectNodes(pXPath));
	}
	
	/**
	 * Wenn die XPath-Anweisung fehlerhaft ist wird eine Exception ausgeloest.
	 * @param pXPath XPath String. Der XPath String muss so aufgebaut sein,
	 * 			                   dass nur ein Element zurueckgegeben wird.
	 * @return XMLElement oder null wenn Element nicht gefunden
	 */
	@Override
	public XMLElement selectSingleNode(String pXPath) {
		Node node = element.selectSingleNode(pXPath);
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
	public String getAttributeName(int pIndex) {
		return element.attribute(pIndex).getName();
	}
	
	@Override
	public String getText() {
		return element.getText();
	}

	@Override
	public void setText(String pText) {
		element.setText(pText);
	}
	
	/**
	 * @param pElementName
	 * @return neues XMLElement
	 */
	@Override
	public XMLElement add(String pElementName) {
		return create(element.addElement(pElementName));
	}
	
	/**
	 * Diese Methode macht aus den Werten eines bestimmten Attributs aller Kindelemente einen Array.
	 * @param pAttributName
	 * @return List
	 */
	@Override
	public List<String> getArray(String pAttributName) {
		List<String> array = new ArrayList<String>();
		for(Iterator<?> iter=getChildren().iterator(); iter.hasNext(); ) {
			XMLElement e = (XMLElement) iter.next();
			array.add(e.getValue(pAttributName));
		}
		return array;
	}
	
	@Override
	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (int i=0; i<element.attributeCount(); i++) {
			Attribute attr = element.attribute(i);
			map.put(attr.getName(), attr.getValue());
		}
		return map;
	}
	
	/**
	 * XML String als Kind an dieses Element anhaengen
	 * @param pXML XML String
	 */
	@Override
	public void append(String pXML) {
		try {
			Document dok = DocumentHelper.parseText(pXML);
			element.add(dok.getRootElement());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Alle direkten Kindelemente anhand Elementnamen loeschen
	 * @param pElementName streng genommen XPath Anweisung fuer selectNodes
	 */
	@Override
	public void removeChildren(String pElementName) {
		List<?> list = element.selectNodes(pElementName);
		for(Iterator<?> iter=list.iterator(); iter.hasNext();) {
			element.remove((Element) iter.next());
		}
	}
	
	/**
	 * @param pBeforeIndex Index vor dem das neue Element eingefuegt werden soll
	 * @param pNewElementName neuer Elementname
	 * @return neues Element
	 */
	@SuppressWarnings("unchecked")
	@Override
	public XMLElement insertBefore(int pBeforeIndex, String pNewElementName) {
		Element neu = new DocumentFactory().createElement(pNewElementName);

		int newIndex = -1;
		List<?> c = element.content();
		for (int i = 0; i < c.size(); i++) {
			String n = c.get(i).getClass().getName();
			if(n.endsWith("Element")) {
				newIndex++;
				if (newIndex == pBeforeIndex) {
					pBeforeIndex = i;
					break;
				}
			}
		}

		element.content().add(pBeforeIndex, neu);
		return create(neu);
	}

	/**
	 * @param pElementName Name des zu suchenden XML Elements
	 * @param pStart Startindex ab 0
	 * @return Index des Elementnamens oder -1 wenn nicht gefunden
	 */
	@Override
	public int indexByName(String pElementName, int pStart) {
		List<?> children = element.elements();
		for (int i = pStart; i < children.size(); i++) {
			if (((Element) children.get(i)).getName().equals(pElementName)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Attribut entfernen
	 * @param pElementName
	 */
	@Override
	public void removeAttribute(String pElementName) {
		try {
			element.remove(element.attribute(pElementName));
		} catch (Exception ignored) { 
		}
	}
	
	protected static XMLElement create(Element pElement) {
		return new XMLElementImpl(pElement);
	}
	
	@Override
	public boolean hasAttribute(String pAttributname) {
		return element.attributeValue(pAttributname) != null;
	}

	@Override
	public void removeEmptyAttributes() {
		List<String> zuLoeschendeAttribute = new ArrayList<String>();
		for (int i = 0; i < getAttributeCount(); i++) {
			String name = getAttributeName(i);
			if ("".equals(getValue(name))) {
				zuLoeschendeAttribute.add(name);
			}
		}
		for (String name : zuLoeschendeAttribute) {
			removeAttribute(name);
		}
	}
}
