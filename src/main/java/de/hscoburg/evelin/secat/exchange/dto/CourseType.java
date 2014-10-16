//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.10.08 um 10:13:40 AM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für courseType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="courseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="instructor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="semester" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}semesterType"/>
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="subject" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}subjectType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "courseType", propOrder = {
    "id",
    "instructor",
    "semester",
    "year",
    "subject"
})
public class CourseType {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String instructor;
    @XmlElement(required = true)
    protected SemesterType semester;
    protected int year;
    @XmlElement(required = true)
    protected SubjectType subject;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der instructor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Legt den Wert der instructor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructor(String value) {
        this.instructor = value;
    }

    /**
     * Ruft den Wert der semester-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SemesterType }
     *     
     */
    public SemesterType getSemester() {
        return semester;
    }

    /**
     * Legt den Wert der semester-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SemesterType }
     *     
     */
    public void setSemester(SemesterType value) {
        this.semester = value;
    }

    /**
     * Ruft den Wert der year-Eigenschaft ab.
     * 
     */
    public int getYear() {
        return year;
    }

    /**
     * Legt den Wert der year-Eigenschaft fest.
     * 
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * Ruft den Wert der subject-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SubjectType }
     *     
     */
    public SubjectType getSubject() {
        return subject;
    }

    /**
     * Legt den Wert der subject-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectType }
     *     
     */
    public void setSubject(SubjectType value) {
        this.subject = value;
    }

}
