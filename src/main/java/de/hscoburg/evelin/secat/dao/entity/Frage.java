package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

@Entity
public class Frage extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2970730609022537155L;

	private String text;

	// private FragePosition position;

	private List<Frage_Fragebogen> customFragen;

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
	public List<Frage_Fragebogen> getCustomFragen() {
		return customFragen;
	}

	public void setCustomFragen(List<Frage_Fragebogen> fragebogen) {
		this.customFragen = fragebogen;
	}

	//
	// @Enumerated
	// public FragePosition getPosition() {
	// return position;
	// }
	//
	// public void setPosition(FragePosition position) {
	// this.position = position;
	// }

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
