package de.hscoburg.evelin.secat.util.javafx;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class SeCatResourceBundle extends ResourceBundle {

	private ResourceBundle bundle;

	private SeCatResourceBundle() {
		refreseh();
	}

	public void refreseh() {
		bundle = ResourceBundle.getBundle("words", Locale.getDefault());
	}

	public static SeCatResourceBundle getInstance() {
		return HOLDER.INSTANCE;
	}

	private static class HOLDER {
		private static final SeCatResourceBundle INSTANCE = new SeCatResourceBundle();

		private HOLDER() {

		}

	}

	@Override
	protected Object handleGetObject(String key) {
		// TODO Auto-generated method stub
		return bundle.getObject(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		return bundle.getKeys();
	}
}
