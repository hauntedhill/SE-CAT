package de.hscoburg.evelin.secat.controller.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityEscaper;

import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;

public abstract class BaseXML {

	public static CharSequenceTranslator XML_ESCAPER = StringEscapeUtils.ESCAPE_XML11.with(NumericEntityEscaper.between(0x0a, 0x0a).with(
			NumericEntityEscaper.between(0x80, 0xff)));

	private List<BaseXML> childs;

	public static String generateUniqueId(Fragebogen fb, Item item) {
		return "fb_" + fb.getId() + "_item_" + item.getId();
	}

	public static String generateUniqueId(Fragebogen fb, Frage item) {
		return "fb_" + fb.getId() + "_frage_" + item.getId();
	}

	public BaseXML() {
		childs = new ArrayList<BaseXML>();

	}

	public void addChild(BaseXML xml) {
		childs.add(xml);
	}

	public int getChildSize() {
		return childs.size();
	}

	public void generateXML(StringBuilder xml) {

		getStartXML(xml);

		for (BaseXML xmlChild : childs) {
			xmlChild.generateXML(xml);
		}

		getEndXML(xml);

	}

	public String generateXML() {
		StringBuilder builder = new StringBuilder();
		generateXML(builder);

		return builder.toString();
	}

	public abstract void getStartXML(StringBuilder builder);

	public abstract void getEndXML(StringBuilder builder);

}
