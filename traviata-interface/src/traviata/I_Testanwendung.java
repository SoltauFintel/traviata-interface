package traviata;

import java.util.List;

public interface I_Testanwendung {

	/**
	 * @return Namen aller Testfallzusammenstellungen
	 */
	List<String> getTFZListe();

	/**
	 * @param tfz Name der Testfallzusammenstellung
	 * @return alle Testfälle zu einer TFZ
	 */
	List<I_Testfall> getTestfaelle(String tfz);
}
