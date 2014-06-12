//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.11 um 07:39:45 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

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
 *         &lt;element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="course" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}courseType"/>
 *         &lt;element name="scale" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}scaleType"/>
 *         &lt;element name="property" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}propertyType" minOccurs="0"/>
 *         &lt;element name="perspective" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}perspectiveType" minOccurs="0"/>
 *         &lt;element name="items" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}itemsType"/>
 *         &lt;element name="questions" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}questionsType" minOccurs="0"/>
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
    "creationDate",
    "course",
    "scale",
    "property",
    "perspective",
    "items",
    "questions"
})
@XmlRootElement(name = "questionarie")
public class Questionarie {

    protected int id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar creationDate;
    @XmlElement(required = true)
    protected CourseType course;
    @XmlElement(required = true)
    protected ScaleType scale;
    protected PropertyType property;
    protected PerspectiveType perspective;
    @XmlElement(required = true)
    protected ItemsType items;
    protected QuestionsType questions;

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
     * Ruft den Wert der property-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PropertyType }
     *     
     */
    public PropertyType getProperty() {
        return property;
    }

    /**
     * Legt den Wert der property-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyType }
     *     
     */
    public void setProperty(PropertyType value) {
        this.property = value;
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
     * Ruft den Wert der items-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ItemsType }
     *     
     */
    public ItemsType getItems() {
        return items;
    }

    /**
     * Legt den Wert der items-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemsType }
     *     
     */
    public void setItems(ItemsType value) {
        this.items = value;
    }

    /**
     * Ruft den Wert der questions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link QuestionsType }
     *     
     */
    public QuestionsType getQuestions() {
        return questions;
    }

    /**
     * Legt den Wert der questions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionsType }
     *     
     */
    public void setQuestions(QuestionsType value) {
        this.questions = value;
    }

}
