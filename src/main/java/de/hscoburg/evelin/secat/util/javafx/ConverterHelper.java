package de.hscoburg.evelin.secat.util.javafx;

import java.util.Locale;

import javafx.util.StringConverter;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;

public class ConverterHelper {

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

	public static StringConverter<Lehrveranstaltung> getConverterForLehrveranstaltung() {
		return new StringConverter<Lehrveranstaltung>() {
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

}