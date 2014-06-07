//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.07 um 02:57:41 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für scaleTypeType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="scaleTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FREE"/>
 *     &lt;enumeration value="DISCRETE"/>
 *     &lt;enumeration value="MC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "scaleTypeType")
@XmlEnum
public enum ScaleTypeType {

    FREE,
    DISCRETE,
    MC;

    public String value() {
        return name();
    }

    public static ScaleTypeType fromValue(String v) {
        return valueOf(v);
    }

}
