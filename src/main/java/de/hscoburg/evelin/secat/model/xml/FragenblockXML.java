package de.hscoburg.evelin.secat.model.xml;

/**
 * Klasse zur definition eines Fragenblockes innerhalb des open QUestionarie Formates
 * 
 * @author zuch1000
 * 
 */
public class FragenblockXML extends BaseXML {

	private int number;

	private String name;

	public FragenblockXML(String name, int number) {
		this.number = number;
		this.name = name;
	}

	/**
	 * Gibt den Starttag zurueck.
	 * 
	 * @param builder
	 *            - {@link StringBuilder} zum speichern des Tags.
	 */
	@Override
	public void getStartXML(StringBuilder builder) {
		builder.append("<QUESTIONBLOCK PLACE=\"" + number + "\" TARGETLAYER=\"0\" RANDOMIZED=\"false\" NAME=\"" + escapeString(name) + "\">\n");
	}

	/**
	 * Gibt den Endtag zurueck.
	 * 
	 * @param builder
	 *            - {@link StringBuilder} zum speichern des Tags.
	 */
	@Override
	public void getEndXML(StringBuilder builder) {

		builder.append("</QUESTIONBLOCK>\n\n");
	}

}
