//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.07 um 03:56:09 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für scaleType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="scaleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}scaleTypeType"/>
 *         &lt;element name="rows" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="steps" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="optimum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="minText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refuseAnswer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherAnswer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="choices" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}choicesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaleType", propOrder = {
    "id",
    "name",
    "type",
    "rows",
    "steps",
    "weight",
    "optimum",
    "minText",
    "maxText",
    "refuseAnswer",
    "otherAnswer",
    "choices"
})
public class ScaleType {

    protected int id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected ScaleTypeType type;
    protected Integer rows;
    protected Integer steps;
    protected Integer weight;
    protected Integer optimum;
    protected String minText;
    protected String maxText;
    protected String refuseAnswer;
    protected String otherAnswer;
    @XmlElement(required = true)
    protected ChoicesType choices;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der type-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ScaleTypeType }
     *     
     */
    public ScaleTypeType getType() {
        return type;
    }

    /**
     * Legt den Wert der type-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ScaleTypeType }
     *     
     */
    public void setType(ScaleTypeType value) {
        this.type = value;
    }

    /**
     * Ruft den Wert der rows-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * Legt den Wert der rows-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRows(Integer value) {
        this.rows = value;
    }

    /**
     * Ruft den Wert der steps-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSteps() {
        return steps;
    }

    /**
     * Legt den Wert der steps-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSteps(Integer value) {
        this.steps = value;
    }

    /**
     * Ruft den Wert der weight-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Legt den Wert der weight-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWeight(Integer value) {
        this.weight = value;
    }

    /**
     * Ruft den Wert der optimum-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOptimum() {
        return optimum;
    }

    /**
     * Legt den Wert der optimum-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOptimum(Integer value) {
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

}
