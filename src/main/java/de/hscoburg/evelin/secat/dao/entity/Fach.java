package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

/**
 * Entitie repraesentiert eine Fach in der Datenbank
 * 
 * @author zuch1000
 * 
 */
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

	@OneToMany(mappedBy = "fach", targetEntity = Lehrveranstaltung.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Lehrveranstaltung> getLehrveranstaltungen() {
		return lehrveranstaltungen;
	}

	public void setLehrveranstaltungen(List<Lehrveranstaltung> lehrveranstaltungen) {
		this.lehrveranstaltungen = lehrveranstaltungen;
	}

}
