//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.07.27 um 02:16:29 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MCQuestionScaleType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MCQuestionScaleType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}scaleType">
 *       &lt;sequence>
 *         &lt;element name="refuseAnswer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherAnswer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="choices" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}choicesType"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MCQuestionScaleType", propOrder = {
    "refuseAnswer",
    "otherAnswer",
    "choices",
    "weight"
})
public class MCQuestionScaleType
    extends ScaleType
{

    protected String refuseAnswer;
    protected String otherAnswer;
    @XmlElement(required = true)
    protected ChoicesType choices;
    protected int weight;

    /**
     * Ruft den Wert der refuseAnswer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefuseAnswer() {
        return refuseAnswer;
    }

    /**
     * Legt den Wert der refuseAnswer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefuseAnswer(String value) {
        this.refuseAnswer = value;
    }

    /**
     * Ruft den Wert der otherAnswer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherAnswer() {
        return otherAnswer;
    }

    /**
     * Legt den Wert der otherAnswer-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherAnswer(String value) {
        this.otherAnswer = value;
    }

    /**
     * Ruft den Wert der choices-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ChoicesType }
     *     
     */
    public ChoicesType getChoices() {
        return choices;
    }

    /**
     * Legt den Wert der choices-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ChoicesType }
     *     
     */
    public void setChoices(ChoicesType value) {
        this.choices = value;
    }

    /**
     * Ruft den Wert der weight-Eigenschaft ab.
     * 
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Legt den Wert der weight-Eigenschaft fest.
     * 
     */
    public void setWeight(int value) {
        this.weight = value;
    }

}
