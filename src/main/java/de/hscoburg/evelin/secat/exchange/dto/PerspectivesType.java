//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.24 um 09:24:55 PM CEST 
//


package de.hscoburg.evelin.secat.exchange.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r perspectivesType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="perspectivesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="perspective" type="{http://www.hs-coburg.de/evelin/secat/exchange/1.0}perspectiveType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "perspectivesType", propOrder = {
    "perspective"
})
public class PerspectivesType {

    @XmlElement(required = true)
    protected List<PerspectiveType> perspective;

    /**
     * Gets the value of the perspective property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the perspective property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerspective().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerspectiveType }
     * 
     * 
     */
    public List<PerspectiveType> getPerspective() {
        if (perspective == null) {
            perspective = new ArrayList<PerspectiveType>();
        }
        return this.perspective;
    }

}
