package de.hscoburg.evelin.secat.model.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityEscaper;

import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;

/**
 * Abstrakteklasse zur Definition des open Questionarie Formats
 * 
 * @author zuch1000
 * 
 */
public abstract class BaseXML {

	/**
	 * Globale Instanze des XML_Escapers
	 */
	private static CharSequenceTranslator XML_ESCAPER = StringEscapeUtils.ESCAPE_XML11.with(NumericEntityEscaper.between(0x0a, 0x0a)
			.with(NumericEntityEscaper.between(0x80, 0x84)).with(NumericEntityEscaper.between(0x86, 0xff)));

	/**
	 * Liste mit allen Kind-Elementen
	 */
	private List<BaseXML> childs;

	/**
	 * Methode zum escapen der Texte fuer den export ins QuestorPro Format
	 * 
	 * @param s
	 *            Der zu escapende String
	 * @return Der Escapte {@link String}
	 */
	protected String escapeString(String s) {
		s = XML_ESCAPER.translate(s);
		s = s.replace("…", "&#x2026;");
		return s;

	}

	/**
	 * Generiert eine eindeutige ID fuer ein Item
	 * 
	 * @param fb
	 *            Das {@link Fragebogen}-Object des Items
	 * @param item
	 *            Das {@link Item} dessen ID generiert werden soll
	 * @return Ein {@link String} mit der ID
	 */
	public static String generateUniqueId(Fragebogen fb, Item item) {
		return "fb_" + fb.getId() + "_item_" + item.getId();
	}

	/**
	 * Generiert eine eindeutige ID fuer ein Item
	 * 
	 * @param fb
	 *            Das {@link Fragebogen}-Object des Items
	 * @param item
	 *            Das {@link Frage} dessen ID generiert werden soll
	 * @return Ein {@link String} mit der ID
	 */
	public static String generateUniqueId(Fragebogen fb, Frage item) {
		return "fb_" + fb.getId() + "_frage_" + item.getId();
	}

	/**
	 * Konstruktor zum erzeugen des Objektes
	 */
	public BaseXML() {
		childs = new ArrayList<BaseXML>();

	}

	/**
	 * Fuegt ein Kind dem XML-Baum hinzu.
	 * 
	 * @param xml
	 *            {@link BaseXML}-Object.
	 */
	public final void addChild(BaseXML xml) {
		childs.add(xml);
	}

	/**
	 * Gibt die Anzahl der Kind-Elemente zurueck.
	 * 
	 * @return {@link Integer}
	 */
	public final int getChildSize() {
		return childs.size();
	}

	/**
	 * Generiert das XML ab diesem Element und speichert dies im uebergebenen {@link StringBuilder}
	 * 
	 * @param xml
	 *            {@link StringBuilder} zum speichern des XMLs
	 */
	public final void generateXML(StringBuilder xml) {

		getStartXML(xml);

		for (BaseXML xmlChild : childs) {
			xmlChild.generateXML(xml);
		}

		getEndXML(xml);

	}

	/**
	 * Generiert das XML ab diesem Element und gibt dies als String zurueck.
	 * 
	 * @return {@link String} mit dem XML
	 */
	public final String generateXML() {
		StringBuilder builder = new StringBuilder();
		generateXML(builder);

		return builder.toString();
	}

	/**
	 * Gibt den StartTag des XMLs zurueck.
	 * 
	 * @param builder
	 *            {@link StringBuilder} zum speichern des Tages
	 */
	public abstract void getStartXML(StringBuilder builder);

	/**
	 * Gibt den EndTag des XMLs zurueck.
	 * 
	 * @param builder
	 *            {@link StringBuilder} zum speichern des Tages
	 */
	public abstract void getEndXML(StringBuilder builder);

}
