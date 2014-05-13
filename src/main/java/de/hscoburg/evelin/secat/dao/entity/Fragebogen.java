package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

@Entity
public class Fragebogen extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324256359163782598L;
	private String name;

	private List<Bewertung> bewertungen;

	private List<Item> items;

	private List<Student> studenten;

	private Lehrveranstaltung lehrveranstaltung;

	private Skala skala;

	@ManyToOne(targetEntity = Skala.class, fetch = FetchType.EAGER)
	public Skala getSkala() {
		return skala;
	}

	public void setSkala(Skala skala) {
		this.skala = skala;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "fragebogen", targetEntity = Bewertung.class)
	public List<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}

	@ManyToMany(targetEntity = Item.class, mappedBy = "frageboegen")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@ManyToMany(targetEntity = Student.class)
	public List<Student> getStudenten() {
		return studenten;
	}

	public void setStudenten(List<Student> studenten) {
		this.studenten = studenten;
	}

	@ManyToOne(targetEntity = Lehrveranstaltung.class)
	public Lehrveranstaltung getLehrveranstaltung() {
		return lehrveranstaltung;
	}

	public void setLehrveranstaltung(Lehrveranstaltung lehrveranstaltung) {
		this.lehrveranstaltung = lehrveranstaltung;
	}

	// public void addItem(Item i) {
	// if (items == null) {
	// items = new ArrayList<Item>();
	// }
	// items.add(i);
	// }

}
