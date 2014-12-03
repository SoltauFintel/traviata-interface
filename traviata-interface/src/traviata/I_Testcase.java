/*
 * (C) by Marcus Warm
 */
package traviata;

import traviata.testreport.TbTestcase;

/**
 * Traviata test case
 */
public interface I_Testcase {
	/** INTERN */
	int NORMAL_PRIORITY = 90;

	/**
	 * @return 6 digit testcase id
	 */
	String getId();

	/**
	 * INTERN
	 * <p>Liefert standardmaessig NORMALE_PRIORITAET.
	 * @return desto kleiner die Zahl desto hoeher die Prioritaet
	 */
	int getPriority();

	/**
	 * der wievielte Testfall innerhalb der Gruppe
	 * @return Wert ab 1
	 */
	int getOrderNumber();

	/**
	 * @return Titel der Testfall-Gruppe
	 */
	String getGroup();

	/**
	 * @return Testfall Titel
	 */
	String getTitle();

	/**
	 * Weist dem Testfall einen Ordner zu, in dem dieser Dateien ablegen darf.
	 * @param path Der Ordner wird ggf. angelegt. Muss mit Pfadtrennzeichen enden.
	 * @throws IllegalArgumentException wenn path leer oder null
	 */
	void setPath(String path);
	
	/**
	 * @return Pfad
	 */
	String getPath();

	/**
	 * Testfall ausfuehren
	 * @throws RuntimeException wenn setPfad() vorher nicht aufgerufen wurde
	 */
	void start();

	/**
	 * @return true wenn Testfall fachlich erfolgreich war,
	 * false wenn Testfall nicht fachlich erfolgreich war,
	 * null wenn Testfall noch nicht ausgefuehrt worden ist
	 */
	Boolean successfully();

	/**
	 * @return test case report
	 */
	TbTestcase getTestreport();
	
	/**
	 * @return compact error message (for unique determination)
	 */
	String getErrorMessage();

	/**
	 * @return Console output
	 */
	String getConsoleOutput();

	/**
	 * @return Script
	 */
	String getScript();
	
	/**
	 * @return Script name
	 */
	String getScriptname();
	
	/**
	 * @return Produktname
	 */
	String getClassification();
	
	/**
	 * @return mehrzeilige Versionsangaben
	 */
	String getVersionInformation();
	
	/**
	 * @return true wenn Testfall abgebrochen worden ist
	 */
	boolean isAborted();
}
