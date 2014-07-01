package de.hscoburg.evelin.secat.model.xml;

/**
 * Stellt eine DiskreteFrage im open Questionarie Format dar.
 * 
 * @author zuch1000
 * 
 */
public class DiskretefrageXML extends BaseXML {

	private int place;

	private String minText;

	private String maxText;

	private int weight;

	private int steps;

	private int optimum;

	private String frage;

	private String id;

	public DiskretefrageXML(String id, int place, int weight, int steps, int optimum, String minText, String maxText, String frage) {
		this.place = place;
		this.minText = minText;
		this.maxText = maxText;
		this.weight = weight;
		this.steps = steps;
		this.frage = frage;
		this.optimum = optimum;
		this.id = id;

	}

	// <SCALEDQUESTION PLACE="4" MINTEXT="trifft &#xfc;berhaupt nicht zu" ZWANG="false" WEIGHT="1" STEPS="4" TUTOR="false"
	// MAXTEXT="trifft voll zu" OPTIMUM="0"
	// TEXT="Es f&#xe4;llt mir aufgrund der Lehrveranstaltung leichter, das Wesentliche an einer Sache zu erkennen.">
	// <META KEY="METAKEY_MARKING_SIZE" VALUE="0.25"/>
	// <META KEY="FIXED_IN_PERMUTATION" VALUE="f"/>
	// <META KEY="METAKEY_USE_SLIDER" VALUE="f"/>
	// </SCALEDQUESTION>

	/**
	 * Gibt den Starttag zurueck.
	 * 
	 * @param builder
	 *            - {@link StringBuilder} zum speichern des Tags.
	 */
	@Override
	public void getStartXML(StringBuilder builder) {

		builder.append("<SCALEDQUESTION PLACE=\"" + place + "\" MINTEXT=\"" + XML_ESCAPER.translate(minText) + "\" ZWANG=\"false\" WEIGHT=\"" + weight
				+ "\" STEPS=\"" + steps + "\" TUTOR=\"false\" MAXTEXT=\"" + XML_ESCAPER.translate(maxText) + "\" OPTIMUM=\"1\" TEXT=\""
				+ XML_ESCAPER.translate(frage) + "\">\n");

		builder.append("<META KEY=\"METAKEY_MARKING_SIZE\" VALUE=\"0.25\"/>\n");
		builder.append("<META KEY=\"FIXED_IN_PERMUTATION\" VALUE=\"f\"/>\n");
		builder.append("<META KEY=\"METAKEY_USE_SLIDER\" VALUE=\"f\"/>\n");
		builder.append("<META KEY=\"METAKEY_CODEPLAN_VALUE\" VALUE=\"" + id + "\"/>\n");

	}

	/**
	 * Gibt den Endtag zurueck.
	 * 
	 * @param builder
	 *            - {@link StringBuilder} zum speichern des Tags.
	 */
	@Override
	public void getEndXML(StringBuilder builder) {
		builder.append("</SCALEDQUESTION>\n");
	}

}
