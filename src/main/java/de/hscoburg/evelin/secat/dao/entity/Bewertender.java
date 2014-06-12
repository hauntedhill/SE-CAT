package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entitie repraesentiert einen Bewertenden in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Bewertender extends BaseEntity {

	private static final long serialVersionUID = 6291965813651020474L;

	private String name;

	private Perspektive perspektive;

	private List<Bewertung> bewertungen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(targetEntity = Perspektive.class)
	public Perspektive getPerspektive() {
		return perspektive;
	}

	public void setPerspektive(Perspektive perspektive) {
		this.perspektive = perspektive;
	}

	@OneToMany(mappedBy = "bewertender", targetEntity = Bewertung.class)
	public List<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}

}
