//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.07 um 02:57:41 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für itemType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="itemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="area" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}areaType"/>
 *         &lt;element name="properties" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}propertieType" maxOccurs="unbounded"/>
 *         &lt;element name="perspectives" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}perspectiveType" maxOccurs="unbounded"/>
 *         &lt;element name="evaluations" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}evaluationType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemType", propOrder = {
    "id",
    "name",
    "frage",
    "area",
    "properties",
    "perspectives",
    "evaluations"
})
public class ItemType {

    protected int id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String frage;
    @XmlElement(required = true)
    protected AreaType area;
    @XmlElement(required = true)
    protected List<PropertieType> properties;
    @XmlElement(required = true)
    protected List<PerspectiveType> perspectives;
    @XmlElement(required = true)
    protected List<EvaluationType> evaluations;

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
     * Ruft den Wert der frage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrage() {
        return frage;
    }

    /**
     * Legt den Wert der frage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrage(String value) {
        this.frage = value;
    }

    /**
     * Ruft den Wert der area-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AreaType }
     *     
     */
    public AreaType getArea() {
        return area;
    }

    /**
     * Legt den Wert der area-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaType }
     *     
     */
    public void setArea(AreaType value) {
        this.area = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertieType }
     * 
     * 
     */
    public List<PropertieType> getProperties() {
        if (properties == null) {
            properties = new ArrayList<PropertieType>();
        }
        return this.properties;
    }

    /**
     * Gets the value of the perspectives property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the perspectives property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerspectives().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerspectiveType }
     * 
     * 
     */
    public List<PerspectiveType> getPerspectives() {
        if (perspectives == null) {
            perspectives = new ArrayList<PerspectiveType>();
        }
        return this.perspectives;
    }

    /**
     * Gets the value of the evaluations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the evaluations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvaluations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EvaluationType }
     * 
     * 
     */
    public List<EvaluationType> getEvaluations() {
        if (evaluations == null) {
            evaluations = new ArrayList<EvaluationType>();
        }
        return this.evaluations;
    }

}
