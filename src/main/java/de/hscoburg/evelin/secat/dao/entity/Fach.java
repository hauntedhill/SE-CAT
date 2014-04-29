package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

@Entity
public class Fach extends StammdatenEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5302664171694242878L;
	private String name;

	private List<Lehrveranstaltung> lehrveranstaltungen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "fach", targetEntity = Lehrveranstaltung.class)
	public List<Lehrveranstaltung> getLehrveranstaltungen() {
		return lehrveranstaltungen;
	}

	public void setLehrveranstaltungen(List<Lehrveranstaltung> lehrveranstaltungen) {
		this.lehrveranstaltungen = lehrveranstaltungen;
	}

}
