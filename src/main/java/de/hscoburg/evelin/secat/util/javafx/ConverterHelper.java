package de.hscoburg.evelin.secat.util.javafx;

import java.util.Locale;

import javafx.util.StringConverter;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;

/**
 * Klasse fuer allgemein benutzbare Converter fuer die entsprechende Entities.
 * 
 * @author zuch1000
 * 
 */
public class ConverterHelper {

	/**
	 * Gibt den Converter fuer die {@link Perspektive} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Perspektive> getConverterForPerspektive() {
		return new StringConverter<Perspektive>() {
			@Override
			public String toString(Perspektive object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Perspektive fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Eigenschaft} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Eigenschaft> getConverterForEigenschaft() {
		return new StringConverter<Eigenschaft>() {
			@Override
			public String toString(Eigenschaft object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Eigenschaft fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Lehrveranstaltung} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Lehrveranstaltung> getConverterForLehrveranstaltung() {
		return new StringConverter<Lehrveranstaltung>() {
			@SuppressWarnings("deprecation")
			@Override
			public String toString(Lehrveranstaltung t) {
				if (t == null) {
					return "";
				}
				return t.getFach().getName() + " " + t.getSemester().name() + t.getJahr().getYear() + " " + t.getDozent();
			}

			@Override
			public Lehrveranstaltung fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Skala} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Skala> getConverterForSkala() {
		return new StringConverter<Skala>() {
			@Override
			public String toString(Skala object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Skala fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Fach} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Fach> getConverterForFach() {
		return new StringConverter<Fach>() {
			@Override
			public String toString(Fach object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Fach fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Locale} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Locale> getConverterForLocale() {
		return new StringConverter<Locale>() {
			@Override
			public String toString(Locale object) {
				if (object == null) {
					return "";
				}
				return object.getDisplayName() + " (" + object.getLanguage() + ("".equals(object.getCountry().trim()) ? "" : "_") + object.getCountry() + ")";

			}

			@Override
			public Locale fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Fragebogen} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Fragebogen> getConverterForFragebogen() {
		return new StringConverter<Fragebogen>() {
			@Override
			public String toString(Fragebogen object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Fragebogen fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link Item} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<Item> getConverterForItem() {
		return new StringConverter<Item>() {
			@Override
			public String toString(Item object) {
				if (object == null) {
					return "";
				}
				return object.getName();
			}

			@Override
			public Item fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

	/**
	 * Gibt den Converter fuer die {@link FragePosition} zurueck.
	 * 
	 * @return ein {@link StringConverter}-Object.
	 */
	public static StringConverter<FragePosition> getConverterForPosition() {
		return new StringConverter<FragePosition>() {
			@Override
			public String toString(FragePosition object) {
				if (object.equals(FragePosition.TOP)) {
					return SeCatResourceBundle.getInstance().getString("scene.all.beginning");
				}
				return SeCatResourceBundle.getInstance().getString("scene.all.end");
			}

			@Override
			public FragePosition fromString(String string) {
				throw new RuntimeException("not required for non editable ComboBox");
			}

		};
	}

}
