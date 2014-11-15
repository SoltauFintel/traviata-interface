package traviata;

import traviata.testbericht.TbTestfall;

public interface I_Testfall {
	/** INTERN */
	int NORMALE_PRIORITAET = 90;

	/**
	 * @return 6stellige Testfall Id
	 */
	String getId();

	/**
	 * INTERN
	 * <p>Liefert standardm��ig NORMALE_PRIORITAET.
	 * @return desto kleiner die Zahl desto h�her die Priorit�t
	 */
	int getPrioritaet();

	/**
	 * der wievielte Testfall innerhalb der Gruppe
	 * @return Wert ab 1
	 */
	int getOrdnungsnummer();

	/**
	 * @return Titel der Testfall-Gruppe
	 */
	String getGruppe();

	/**
	 * @return Testfall Titel
	 */
	String getTitel();

	/**
	 * Weist dem Testfall einen Ordner zu, in dem dieser Dateien ablegen darf.
	 * @param path Der Ordner wird ggf. angelegt. Muss mit Pfadtrennzeichen enden.
	 * @throws IllegalArgumentException wenn path leer oder null
	 */
	void setPfad(String path);
	
	/**
	 * @return Pfad
	 */
	String getPfad();

	/**
	 * Testfall ausf�hren
	 * @throws RuntimeException wenn setPfad() vorher nicht aufgerufen wurde
	 */
	void start();

	/**
	 * @return true wenn Testfall fachlich erfolgreich war,
	 * false wenn Testfall nicht fachlich erfolgreich war,
	 * null wenn Testfall noch nicht ausgef�hrt worden ist
	 */
	Boolean fachlichErfolgreich();

	/**
	 * @return Testbericht
	 */
	TbTestfall getTestbericht();
	
	/**
	 * @return kompakte Fehlermeldung (f�r unique-Bestimmung)
	 */
	String getFehlermeldung();

	/**
	 * @return Console output
	 */
	String getConsoleOutput();

	/**
	 * @return Script
	 */
	String getScript();
	
	/**
	 * @return Dateiname der Scriptdatei
	 */
	String getScriptDateiname();
	
	/**
	 * @return Produktname
	 */
	String getProdukt();
	
	/**
	 * @return mehrzeilige Versionsangaben
	 */
	String getVersionsangaben();
	
	/**
	 * @return true wenn Testfall abgebrochen worden ist
	 */
	boolean isAbgebrochen();
}
