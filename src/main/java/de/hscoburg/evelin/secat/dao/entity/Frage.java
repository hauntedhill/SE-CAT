package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;

@Entity
public class Frage extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2970730609022537155L;

	private String text;

	private FragePosition position;

	private List<Fragebogen> fragebogen;

	private Skala skala;

	private List<Bewertung> bewertungen;

	@ManyToOne(targetEntity = Skala.class)
	public Skala getSkala() {
		return skala;
	}

	public void setSkala(Skala skala) {
		this.skala = skala;
	}

	@ManyToMany(targetEntity = Fragebogen.class)
	public List<Fragebogen> getFragebogen() {
		return fragebogen;
	}

	public void setFragebogen(List<Fragebogen> fragebogen) {
		this.fragebogen = fragebogen;
	}

	@Enumerated
	public FragePosition getPosition() {
		return position;
	}

	public void setPosition(FragePosition position) {
		this.position = position;
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

}
