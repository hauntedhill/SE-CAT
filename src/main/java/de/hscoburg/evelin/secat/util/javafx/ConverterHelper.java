package de.hscoburg.evelin.secat.util.javafx;

import java.util.Locale;

import javafx.util.StringConverter;
import de.hscoburg.evelin.secat.dao.entity.Fach;

public class ConverterHelper {

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
