package de.hscoburg.evelin.secat.controller.xml;

import java.util.List;

public class MultipleChoicefrageXML extends BaseXML {

	private List<String> choices;

	private String defaultAnswer;

	private int weight;

	private String text;

	private int place;

	private String id;

	public MultipleChoicefrageXML(String defaultAnswer, int weight, String text, List<String> choices, String id, int place) {
		this.choices = choices;
		this.defaultAnswer = defaultAnswer;
		this.weight = weight;
		this.text = text;
		this.place = place;
		this.id = id;
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

	@Override
	public void getStartXML(StringBuilder builder) {
		builder.append("<MCQUESTION PLACE=\"" + place + "\" TEXT=\"" + XML_ESCAPER.translate(text) + "\" ZWANG=\"false\" WEIGHT=\"" + weight
				+ "\" OTHERANSWER=\"" + XML_ESCAPER.translate(defaultAnswer) + "\" TUTOR=\"false\">\n");
		builder.append("<META KEY=\"METAKEY_MC_WEB_LAYOUT\" VALUE=\"one_col\"/>\n");
		builder.append("<META KEY=\"METAKEY_MARKING_SIZE\" VALUE=\"0.25\"/>\n");
		builder.append("<META KEY=\"FIXED_IN_PERMUTATION\" VALUE=\"f\"/>\n");
		builder.append("<META KEY=\"METAKEY_MC_WEIGHT_OPT\" VALUE=\"1\"/>\n");
		builder.append("<META KEY=\"METAKEY_CODEPLAN_VALUE\" VALUE=\"" + id + "\"/>\n");

		for (int i = 0; i < choices.size(); i++) {
			builder.append("<ANSWER PLACE=\"" + (i + 1) + "\" TEXT=\"" + XML_ESCAPER.translate(choices.get(i)) + "\" WEIGHT=\"0\"/>\n");
		}

	}

	@Override
	public void getEndXML(StringBuilder builder) {
		builder.append("</MCQUESTION>\n");

	}

}
