package de.hscoburg.evelin.secat.dao.entity.base;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Basis Klasse fuer alle Entities
 * 
 * @author Haunted
 * 
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * Default serial Version UID
	 */
	private static final long serialVersionUID = 2785705211668271930L;

	/**
	 * Instance der ID
	 */
	private Integer id;

	/**
	 * Gibt die ID der Entity zurueck.
	 * 
	 * @return Ein {@link Integer}-Object.
	 */
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	/**
	 * Setzt die ID der Entity
	 * 
	 * @param id
	 *            Ein {@link Integer}-Object.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gibt die String repraesentation der Entity zurueck.
	 * 
	 * @return Ein {@link String}-Object
	 */
	@Override
	public String toString() {

		return this.getClass().getName() + "-ID: " + id;

	}

	/**
	 * Berechnet des HashCode der Entity
	 * 
	 * @return Ein {@link Integer}-Wert der den hashCode darstellt
	 */
	@Override
	public int hashCode() {

		int hash = 1;
		hash = hash * 17 + this.getClass().getName().hashCode();
		hash = hash * 31 + new Integer(id).hashCode();
		return hash;

	}

	/**
	 * Prueft die Entity auf Gleichheit zu der uebergebene Entity
	 * 
	 * @param obj
	 *            {@link Object} mit dem die Gleichheit geprueft werden soll
	 * 
	 * @return true wenn gleich, andernfalls false
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass().isAssignableFrom(obj.getClass()) && ((BaseEntity) obj).getId() != null) {

			return ((BaseEntity) obj).getId().equals(this.getId());
		}
		return false;

	}

}