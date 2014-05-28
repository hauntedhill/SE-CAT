package de.hscoburg.evelin.secat.controller.xml;

import java.util.ArrayList;
import java.util.List;

import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;

public abstract class BaseXML {

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

	public StringBuilder generateXML() {
		StringBuilder xml = new StringBuilder();

		xml.append(getStartXML());

		for (BaseXML xmlChild : childs) {
			xml.append(xmlChild.generateXML());
		}

		xml.append(getEndXML());

		return xml;

	}

	public abstract StringBuilder getStartXML();

	public abstract StringBuilder getEndXML();

}
