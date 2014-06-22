//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.22 um 12:32:03 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für DiscreteQuestionScaleType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DiscreteQuestionScaleType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}scaleType">
 *       &lt;sequence>
 *         &lt;element name="steps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="optimum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiscreteQuestionScaleType", propOrder = {
    "steps",
    "weight",
    "optimum",
    "minText",
    "maxText"
})
public class DiscreteQuestionScaleType
    extends ScaleType
{

    protected int steps;
    protected int weight;
    protected int optimum;
    @XmlElement(required = true)
    protected String minText;
    @XmlElement(required = true)
    protected String maxText;

    /**
     * Ruft den Wert der steps-Eigenschaft ab.
     * 
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Legt den Wert der steps-Eigenschaft fest.
     * 
     */
    public void setSteps(int value) {
        this.steps = value;
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

    /**
     * Ruft den Wert der optimum-Eigenschaft ab.
     * 
     */
    public int getOptimum() {
        return optimum;
    }

    /**
     * Legt den Wert der optimum-Eigenschaft fest.
     * 
     */
    public void setOptimum(int value) {
        this.optimum = value;
    }

    /**
     * Ruft den Wert der minText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinText() {
        return minText;
    }

    /**
     * Legt den Wert der minText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinText(String value) {
        this.minText = value;
    }

    /**
     * Ruft den Wert der maxText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxText() {
        return maxText;
    }

    /**
     * Legt den Wert der maxText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxText(String value) {
        this.maxText = value;
    }

}
