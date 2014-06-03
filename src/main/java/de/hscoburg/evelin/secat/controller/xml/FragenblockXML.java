package de.hscoburg.evelin.secat.controller.xml;

public class FragenblockXML extends BaseXML {

	private int number;

	private String name;

	public FragenblockXML(String name, int number) {
		this.number = number;
		this.name = name;
	}

	@Override
	public void getStartXML(StringBuilder builder) {
		builder.append("<QUESTIONBLOCK PLACE=\"" + number + "\" TARGETLAYER=\"0\" RANDOMIZED=\"false\" NAME=\"" + XML_ESCAPER.translate(name) + "\">\n");
	}

	@Override
	public void getEndXML(StringBuilder builder) {

		builder.append("</QUESTIONBLOCK>\n\n");
	}

}
