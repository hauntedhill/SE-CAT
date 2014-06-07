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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="items" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}itemType" maxOccurs="unbounded"/>
 *         &lt;element name="course" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}courseType"/>
 *         &lt;element name="scale" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}scaleType"/>
 *         &lt;element name="propertie" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}propertieType" minOccurs="0"/>
 *         &lt;element name="perspective" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}perspectiveType" minOccurs="0"/>
 *         &lt;element name="questions" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}questionType" minOccurs="0"/>
 *         &lt;element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "items",
    "course",
    "scale",
    "propertie",
    "perspective",
    "questions",
    "creationDate"
})
@XmlRootElement(name = "Questionarie")
public class Questionarie {

    protected int id;
    @XmlElement(required = true)
    protected List<ItemType> items;
    @XmlElement(required = true)
    protected CourseType course;
    @XmlElement(required = true)
    protected ScaleType scale;
    protected PropertieType propertie;
    protected PerspectiveType perspective;
    protected QuestionType questions;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar creationDate;

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
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemType }
     * 
     * 
     */
    public List<ItemType> getItems() {
        if (items == null) {
            items = new ArrayList<ItemType>();
        }
        return this.items;
    }

    /**
     * Ruft den Wert der course-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CourseType }
     *     
     */
    public CourseType getCourse() {
        return course;
    }

    /**
     * Legt den Wert der course-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseType }
     *     
     */
    public void setCourse(CourseType value) {
        this.course = value;
    }

    /**
     * Ruft den Wert der scale-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ScaleType }
     *     
     */
    public ScaleType getScale() {
        return scale;
    }

    /**
     * Legt den Wert der scale-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ScaleType }
     *     
     */
    public void setScale(ScaleType value) {
        this.scale = value;
    }

    /**
     * Ruft den Wert der propertie-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PropertieType }
     *     
     */
    public PropertieType getPropertie() {
        return propertie;
    }

    /**
     * Legt den Wert der propertie-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertieType }
     *     
     */
    public void setPropertie(PropertieType value) {
        this.propertie = value;
    }

    /**
     * Ruft den Wert der perspective-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PerspectiveType }
     *     
     */
    public PerspectiveType getPerspective() {
        return perspective;
    }

    /**
     * Legt den Wert der perspective-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PerspectiveType }
     *     
     */
    public void setPerspective(PerspectiveType value) {
        this.perspective = value;
    }

    /**
     * Ruft den Wert der questions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link QuestionType }
     *     
     */
    public QuestionType getQuestions() {
        return questions;
    }

    /**
     * Legt den Wert der questions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionType }
     *     
     */
    public void setQuestions(QuestionType value) {
        this.questions = value;
    }

    /**
     * Ruft den Wert der creationDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Legt den Wert der creationDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
    }

}
