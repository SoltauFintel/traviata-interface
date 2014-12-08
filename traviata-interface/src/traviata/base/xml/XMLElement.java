package traviata.base.xml;

import java.util.List;
import java.util.Map;

/**
 * XML Element Interface
 */
public interface XMLElement {

	String NEWLINE = "[^NEWLINE^]";

	/**
	 * Attributzugriff via Attributname
	 * @param attributname Attributname
	 * @return Attributinhalt als String
	 */
	String getValue(String attributname);

	/**
	 * Subelemente
	 * @return Liste von I_Element Objekten
	 */
	List<XMLElement> getChildren();

	/**
	 * @return Elementname
	 */
	String getName();

	/**
	 * Meta-Information
	 * @return Anzahl Attribute
	 */
	int getAttributeCount();
	
	/**
	 * Meta-Information
	 * @param pIndex Attributindex ab 0
	 * @return Attributname
	 */
	String getAttributeName(int pIndex);
	
	/**
	 * Schreibzugriff auf ein Attribut
	 * @param attributname
	 * @param value
	 */
	void setValue(String attributname, String value);
	
	/**
	 * Schreibzugriff auf ein Attribut
	 * @param attributname
	 * @param value mehrzeiliger Inhalt
	 */
	void setMultiLineValue(String attributname, String value);
	
	/**
	 * Attributzugriff via Attributname
	 * <p>Mehrzeilige Attributinhalte werden mit "\n" getrennt.
	 * @param attributname Attributname
	 * @return Attributinhalt als String
	 */
	String getMultiLineValue(String attributname);
	
	/**
	 * @return XML String
	 */
	String getXML();

	/**
	 * Wenn die XPath-Anweisung fehlerhaft ist wird eine Exception ausgeloest.
	 * @param xpath XPath String. Der XPath String muss so aufgebaut sein,
	 * 			                   dass nur Elemente zurueckgegeben werden.
	 * @return XMLElement Liste
	 */
	List<XMLElement> selectNodes(String xpath);

	/**
	 * Wenn die XPath-Anweisung fehlerhaft ist wird eine Exception ausgeloest.
	 * @param xpath XPath String. Der XPath String muss so aufgebaut sein,
	 * 			                   dass nur ein Element zurueckgegeben wird.
	 * @return XMLElement oder null wenn Element nicht gefunden
	 */
	XMLElement selectSingleNode(String xpath);

	/**
	 * @return innerer Text
	 */
	String getText();

	/**
	 * @param text innerer Text
	 */
	void setText(String text);

	/**
	 * @param elementName
	 * @return neues XMLElement
	 */
	XMLElement add(String elementName);

	/**
	 * Diese Methode macht aus den Werten eines bestimmten Attributs aller Kindelemente einen Array.
	 * @param attributname
	 * @return List
	 */
	List<String> getArray(String attributname);

	/**
	 * Diese Methode macht aus allen Attributen eine Map.
	 * Die Reihenfolge wird nicht gewaehrleistet.
	 * Innerer Text und Kindelemente werden nicht beruecksichtigt.
	 * @return Map Key: Attributname [String], Value: Attributinhalt [String]
	 */
	Map<String, String> getMap();
	
	/**
	 * XML String als Kind an dieses Element anhaengen
	 * @param xml XML String
	 */
	void append(String xml);

	/**
	 * Alle direkten Kindelemente anhand Elementnamen loeschen
	 * @param elementName streng genommen XPath Anweisung fuer selectNodes
	 */
	void removeChildren(String elementName);

	/**
	 * @param pBeforeIndex Index vor dem das neue Element eingefuegt werden soll
	 * @param pNewElementName neuer Elementname
	 * @return neues Element
	 */
	XMLElement insertBefore(int pBeforeIndex, String pNewElementName);

	/**
	 * @param elementName Name des zu suchenden XML Elements
	 * @param start Startindex ab 0
	 * @return Index des Elementnamens oder -1 wenn nicht gefunden
	 */
	int indexByName(String elementName, int start);

	/**
	 * Attribut entfernen
	 * @param elementName
	 */
	void removeAttribute(String elementName);
	
	/**
	 * @return Anzahl der Kindelemente
	 */
	int getChildrenCount();

	/**
	 * @return true wenn Kindelemente vorhanden sind, sonst false
	 */
	boolean hasChildren();
	
	/**
	 * @param attributname
	 * @return true wenn Attribut existiert, sonst false
	 */
	boolean hasAttribute(String attributname);
	
	/**
	 * Loescht leere Attribute
	 */
	void removeEmptyAttributes();

	/**
	 * @param name neuer Elementname
	 */
	void setName(String name);
	
	/**
	 * @param attributname
	 * @param value
	 */
	void setValueIfNotNull(String attributname, String value);
}
