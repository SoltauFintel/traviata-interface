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
	 * @param pAttributname Attributname
	 * @return Attributinhalt als String
	 */
	String getValue(String pAttributname);

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
	 * @param pAttributname
	 * @param pValue
	 */
	void setValue(String pAttributname, String pValue);
	
	/**
	 * Schreibzugriff auf ein Attribut
	 * @param pAttributname
	 * @param pValue mehrzeiliger Inhalt
	 */
	void setMultiLineValue(String pAttributname, String pValue);
	
	/**
	 * Attributzugriff via Attributname
	 * <p>Mehrzeilige Attributinhalte werden mit "\n" getrennt.
	 * @param pAttributname Attributname
	 * @return Attributinhalt als String
	 */
	String getMultiLineValue(String pAttributname);
	
	/**
	 * @return XML String
	 */
	String getXML();

	/**
	 * Wenn die XPath-Anweisung fehlerhaft ist wird eine Exception ausgeloest.
	 * @param pXPath XPath String. Der XPath String muss so aufgebaut sein,
	 * 			                   dass nur Elemente zurueckgegeben werden.
	 * @return XMLElement Liste
	 */
	List<XMLElement> selectNodes(String pXPath);

	/**
	 * Wenn die XPath-Anweisung fehlerhaft ist wird eine Exception ausgeloest.
	 * @param pXPath XPath String. Der XPath String muss so aufgebaut sein,
	 * 			                   dass nur ein Element zurueckgegeben wird.
	 * @return XMLElement oder null wenn Element nicht gefunden
	 */
	XMLElement selectSingleNode(String pXPath);

	/**
	 * @return innerer Text
	 */
	String getText();

	/**
	 * @param pText innerer Text
	 */
	void setText(String pText);

	/**
	 * @param pElementName
	 * @return neues XMLElement
	 */
	XMLElement add(String pElementName);

	/**
	 * Diese Methode macht aus den Werten eines bestimmten Attributs aller Kindelemente einen Array.
	 * @param pAttributName
	 * @return List
	 */
	List<String> getArray(String pAttributName);

	/**
	 * Diese Methode macht aus allen Attributen eine Map.
	 * Die Reihenfolge wird nicht gewaehrleistet.
	 * Innerer Text und Kindelemente werden nicht beruecksichtigt.
	 * @return Map Key: Attributname [String], Value: Attributinhalt [String]
	 */
	Map<String, String> getMap();
	
	/**
	 * XML String als Kind an dieses Element anhaengen
	 * @param pXML XML String
	 */
	void append(String pXML);

	/**
	 * Alle direkten Kindelemente anhand Elementnamen loeschen
	 * @param pElementName streng genommen XPath Anweisung fuer selectNodes
	 */
	void removeChildren(String pElementName);

	/**
	 * @param pBeforeIndex Index vor dem das neue Element eingefuegt werden soll
	 * @param pNewElementName neuer Elementname
	 * @return neues Element
	 */
	XMLElement insertBefore(int pBeforeIndex, String pNewElementName);

	/**
	 * @param pElementName Name des zu suchenden XML Elements
	 * @param pStart Startindex ab 0
	 * @return Index des Elementnamens oder -1 wenn nicht gefunden
	 */
	int indexByName(String pElementName, int pStart);

	/**
	 * Attribut entfernen
	 * @param pElementName
	 */
	void removeAttribute(String pElementName);
	
	/**
	 * @return Anzahl der Kindelemente
	 */
	int getChildrenCount();

	/**
	 * @return true wenn Kindelemente vorhanden sind, sonst false
	 */
	boolean hasChildren();
	
	/**
	 * @param pAttributname
	 * @return true wenn Attribut existiert, sonst false
	 */
	boolean hasAttribute(String pAttributname);
	
	/**
	 * Loescht leere Attribute
	 */
	void removeEmptyAttributes();

	/**
	 * @param name neuer Elementname
	 */
	void setName(String name);
}
