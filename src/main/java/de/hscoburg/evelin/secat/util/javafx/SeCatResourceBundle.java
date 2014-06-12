package de.hscoburg.evelin.secat.util.javafx;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasse zum zugriff auf das RessourceBundle von SE-CAT
 * 
 * @author zuch1000
 * 
 */
public class SeCatResourceBundle extends ResourceBundle {

	/**
	 * Instance des ResourcebUndles
	 */
	private ResourceBundle bundle;

	/**
	 * Konstruktor zum erzeugen des Objectes
	 */
	private SeCatResourceBundle() {
		refreseh();
	}

	/**
	 * Methode zum erneuten laden des ResourceBundles (zB sprachwechsel)
	 */
	public void refreseh() {
		bundle = ResourceBundle.getBundle("words", Locale.getDefault());
	}

	/**
	 * Methode zum holen einer Instance
	 * 
	 * @return Ein {@link SeCatResourceBundle}-Object.
	 */
	public static SeCatResourceBundle getInstance() {
		return HOLDER.INSTANCE;
	}

	/**
	 * Private Klasse das die einzige in der VM existierende Instance verwaltet.
	 * 
	 * @author zuch1000
	 * 
	 */
	private static class HOLDER {
		/**
		 * Instance der Klasse
		 */
		private static final SeCatResourceBundle INSTANCE = new SeCatResourceBundle();

		/**
		 * Privater Konstruktor
		 */
		private HOLDER() {

		}

	}

	/**
	 * Gibt fuer den uebergebenen key ein Object zurueck.
	 * 
	 * @param key
	 *            - Der zu suchende Eintrag
	 * @return {@link Object} der gefundene Eintrag oder null
	 */
	@Override
	protected Object handleGetObject(String key) {

		return bundle.getObject(key);
	}

	/**
	 * Gibt alle vorhandenen Keys zurueck.
	 * 
	 * @return {@link Enumeration}
	 */
	@Override
	public Enumeration<String> getKeys() {

		return bundle.getKeys();
	}
}
