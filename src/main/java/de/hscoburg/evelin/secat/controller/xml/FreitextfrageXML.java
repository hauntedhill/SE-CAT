package de.hscoburg.evelin.secat.controller.xml;

public class FreitextfrageXML extends BaseXML {

	private int rows;

	private String text;

	private int place;

	private String id;

	public FreitextfrageXML(String id, String text, int place, int rows) {

		this.rows = rows;
		this.text = text;
		this.place = place;
		this.id = id;

		// TODO Auto-generated constructor stub
	}

	// <FREEQUESTION ROWS="4" PLACE="1"
	// TEXT="Bitte geben Sie hier Ihren individuell errechneten Code an. Er errechnet sich wie folgt:&#xa;** Wenn Ihr Tag des Geburtsdatums eine gerade Zahl ist, schreiben Sie ein A, wenn er ungerade ist ein B&#xa;** nehmen Sie die letzten beiden Ziffern Ihrer Matrikelnummer; wenn sie gerade ist multiplizieren Sie sie mit 3, wenn sie ungearde ist, multiplizieren Sie sie mit 2; notieren Sie diese Zahl dreistellig&#xa;** notieren Sie den ersten und zweiten Buchstaben des Vornamens Ihrer Mutter&#xa;** addieren Sie Tag und Monat Ihres eigenen Geburtstags (Beispiel: Geburtstag: 06.02. --&gt;06+02=8); ist das Ergebnis kleiner gleich 10 addieren Sie 20 dazu; ist das Ergebnis der Addition gr&#xf6;&#xdf;er 30 subtrahieren Sie 20 (im Beispiel trifft die erste Bedingung zu, also ist das Ergebnis 28);&#xa;** wenn Sie Informatik im Hauptfach studieren, notieren Sie ein H, wenn nicht, dann ein N "
	// ZWANG="false" PUBLIC="false" TUTOR="false">
	// <META KEY="METAKEY_BARCODE_WIDTH" VALUE="2.0"/>
	// <META KEY="METAKEY_BARCODE_VALIDATION" VALUE=""/>
	// <META KEY="METAKEY_BARCODE_HEIGHT" VALUE="1.0"/>
	// <META KEY="FIXED_IN_PERMUTATION" VALUE="f"/>
	// <META KEY="METAKEY_BARCODE_FIELD_TEXT" VALUE=""/>
	// </FREEQUESTION>

	@Override
	public void getStartXML(StringBuilder builder) {

		builder.append("<FREEQUESTION ROWS=\"" + rows + "\" PLACE=\"" + place + "\" TEXT=\"" + XML_ESCAPER.translate(text)
				+ "\" ZWANG=\"false\" PUBLIC=\"false\" TUTOR=\"false\">\n");
		builder.append("<META KEY=\"METAKEY_BARCODE_WIDTH\" VALUE=\"2.0\"/>\n");
		builder.append("<META KEY=\"METAKEY_BARCODE_VALIDATION\" VALUE=\"\"/>\n");
		builder.append("<META KEY=\"METAKEY_BARCODE_HEIGHT\" VALUE=\"1.0\"/>\n");
		builder.append("<META KEY=\"FIXED_IN_PERMUTATION\" VALUE=\"f\"/>\n");
		builder.append("<META KEY=\"METAKEY_BARCODE_FIELD_TEXT\" VALUE=\"\"/>\n");
		builder.append("<META KEY=\"METAKEY_CODEPLAN_VALUE\" VALUE=\"" + id + "\"/>\n");

	}

	@Override
	public void getEndXML(StringBuilder builder) {

		builder.append("</FREEQUESTION>\n");
	}

}
