package de.hscoburg.evelin.secat.model.xml;

import java.util.List;

/**
 * Definiert eine MultipleChoice Frage innerhalb des open Questionarie Formates
 * 
 * @author zuch1000
 * 
 */
public class MultipleChoicefrageXML extends BaseXML {

	private List<String> choices;

	private String defaultAnswer;

	private String refuseAnswer;

	private int weight;

	private String text;

	private int place;

	private String id;

	public MultipleChoicefrageXML(String defaultAnswer, int weight, String text, List<String> choices, String id, int place, String refuseAnswer) {
		this.choices = choices;
		this.defaultAnswer = defaultAnswer;
		this.weight = weight;
		this.text = text;
		this.place = place;
		this.id = id;
		this.refuseAnswer = refuseAnswer;
	}

	// <MCQUESTION PLACE="1" TEXT="Wetter?" ZWANG="false" WEIGHT="1" OTHERANSWER="sonstiges" TUTOR="false">
	// <META KEY="METAKEY_MC_WEB_LAYOUT" VALUE="one_col"/>
	// <META KEY="METAKEY_MARKING_SIZE" VALUE="0.25"/>
	// <META KEY="FIXED_IN_PERMUTATION" VALUE="f"/>
	// <META KEY="METAKEY_MC_WEIGHT_OPT" VALUE="1"/>
	// <ANSWER PLACE="1" TEXT="sch&#xf6;n" WEIGHT="0"/>
	// <ANSWER PLACE="2" TEXT="schlecht" WEIGHT="0"/>
	// <ANSWER PLACE="3" TEXT="mies" WEIGHT="0"/>
	// </MCQUESTION>
	/**
	 * Gibt den Starttag zurueck.
	 * 
	 * @param builder
	 *            {@link StringBuilder} zum speichern des Tags.
	 */
	@Override
	public void getStartXML(StringBuilder builder) {
		builder.append("<MCQUESTION PLACE=\"" + place + "\" TEXT=\"" + escapeString(text) + "\" ZWANG=\"false\" WEIGHT=\"" + weight + "\" ");

		if (defaultAnswer != null && !"".equals(defaultAnswer)) {
			builder.append(" OTHERANSWER=\"" + escapeString(defaultAnswer) + "\" ");
		}
		if (refuseAnswer != null && !"".equals(refuseAnswer)) {
			builder.append(" REFUSEANSWER=\"" + escapeString(refuseAnswer) + "\" ");
		}
		builder.append(" TUTOR=\"false\">\n");
		builder.append("<META KEY=\"METAKEY_MC_WEB_LAYOUT\" VALUE=\"one_col\"/>\n");
		builder.append("<META KEY=\"METAKEY_MARKING_SIZE\" VALUE=\"0.25\"/>\n");
		builder.append("<META KEY=\"FIXED_IN_PERMUTATION\" VALUE=\"f\"/>\n");
		builder.append("<META KEY=\"METAKEY_MC_WEIGHT_OPT\" VALUE=\"1\"/>\n");
		builder.append("<META KEY=\"METAKEY_CODEPLAN_VALUE\" VALUE=\"" + id + "\"/>\n");

		for (int i = 0; i < choices.size(); i++) {
			builder.append("<ANSWER PLACE=\"" + (i + 1) + "\" TEXT=\"" + escapeString(choices.get(i)) + "\" WEIGHT=\"0\"/>\n");
		}

	}

	/**
	 * Gibt den Endtag zurueck.
	 * 
	 * @param builder
	 *            {@link StringBuilder} zum speichern des Tags.
	 */
	@Override
	public void getEndXML(StringBuilder builder) {
		builder.append("</MCQUESTION>\n");

	}

}
