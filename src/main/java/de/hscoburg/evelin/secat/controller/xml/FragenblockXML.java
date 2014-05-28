package de.hscoburg.evelin.secat.controller.xml;

public class FragenblockXML extends BaseXML {

	private int number;

	private String name;

	public FragenblockXML(String name, int number) {
		this.number = number;
		this.name = name;
	}

	@Override
	public StringBuilder getStartXML() {
		return new StringBuilder().append("<QUESTIONBLOCK PLACE=\"" + number + "\" TARGETLAYER=\"0\" RANDOMIZED=\"false\" NAME=\"" + name + "\">\n");
	}

	@Override
	public StringBuilder getEndXML() {

		return new StringBuilder().append("</QUESTIONBLOCK>\n\n");
	}

}
