package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entity repraesentiert einen Frage in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Frage extends BaseEntity {

	private static final long serialVersionUID = 2970730609022537155L;

	private String text;

	private List<Frage_Fragebogen> frageFragebogen;

	private Skala skala;

	private String name;

	private List<Bewertung> bewertungen;

	@ManyToOne(targetEntity = Skala.class)
	public Skala getSkala() {
		return skala;
	}

	public void setSkala(Skala skala) {
		this.skala = skala;
	}

	@OneToMany(targetEntity = Frage_Fragebogen.class, mappedBy = "frage")
	public List<Frage_Fragebogen> getFrageFragebogen() {
		return frageFragebogen;
	}

	public void setFrageFragebogen(List<Frage_Fragebogen> fragebogen) {
		this.frageFragebogen = fragebogen;
	}

	@Column(length = 1024)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@OneToMany(targetEntity = Bewertung.class, mappedBy = "frage")
	public List<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
