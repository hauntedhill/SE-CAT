package de.hscoburg.evelin.secat.util.spring;

import java.io.IOException;
import java.util.Properties;

public class PropertieManager extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String KEY_DB_HOST = "secat.db.host";

	public static final String KEY_DB_SCHEMA = "secat.db.schema";

	public static final String KEY_DB_USER = "secat.db.user";

	public static final String KEY_DB_PASSWORD = "secat.db.password";

	private PropertieManager() {

		try {
			this.load(SpringDefaultConfiguration.class.getResourceAsStream("/secat.properties"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String getProperty(String key) {
		return System.getProperty(key, super.getProperty(key));
	}

	public static PropertieManager getInstance() {
		return HOLDER.INSTANCE;
	}

	private static class HOLDER {
		private static final PropertieManager INSTANCE = new PropertieManager();

		private HOLDER() {

		}

	}

}
