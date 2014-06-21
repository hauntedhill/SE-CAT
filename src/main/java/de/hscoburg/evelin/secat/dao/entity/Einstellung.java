package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;

/**
 * Entity repraesentiert eine Eigenschaft in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Einstellung extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2346776847596178844L;

	private EinstellungenType name;

	private String wert;

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	@Enumerated(EnumType.STRING)
	public EinstellungenType getName() {
		return name;
	}

	public void setName(EinstellungenType name) {
		this.name = name;
	}

}
