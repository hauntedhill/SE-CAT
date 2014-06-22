//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.22 um 12:32:03 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für FreeQuestionScaleType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="FreeQuestionScaleType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}scaleType">
 *       &lt;sequence>
 *         &lt;element name="rows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreeQuestionScaleType", propOrder = {
    "rows"
})
public class FreeQuestionScaleType
    extends ScaleType
{

    protected int rows;

    /**
     * Ruft den Wert der rows-Eigenschaft ab.
     * 
     */
    public int getRows() {
        return rows;
    }

    /**
     * Legt den Wert der rows-Eigenschaft fest.
     * 
     */
    public void setRows(int value) {
        this.rows = value;
    }

}
