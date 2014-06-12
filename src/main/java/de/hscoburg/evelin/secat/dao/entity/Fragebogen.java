package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entitie repraesentiert einen Fragebogen in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Fragebogen extends BaseEntity {

	private static final long serialVersionUID = -5324256359163782598L;
	private String name;

	private List<Bewertung> bewertungen;

	private List<Item> items;

	private List<Student> studenten;

	private Lehrveranstaltung lehrveranstaltung;

	private Skala skala;

	private List<Frage_Fragebogen> frageFragebogen;

	private Eigenschaft eigenschaft;

	private Perspektive perspektive;

	private Date erstellungsDatum;

	private Boolean exportiert;

	@ManyToOne(targetEntity = Skala.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
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

	@OneToMany(mappedBy = "fragebogen", targetEntity = Bewertung.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}

	@ManyToMany(targetEntity = Item.class, mappedBy = "frageboegen", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
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

	@OneToMany(targetEntity = Frage_Fragebogen.class, mappedBy = "fragebogen", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Frage_Fragebogen> getFrageFragebogen() {
		return frageFragebogen;
	}

	public void setFrageFragebogen(List<Frage_Fragebogen> fragen) {
		this.frageFragebogen = fragen;
	}

	public void addFrageFragebogen(Frage_Fragebogen f) {
		if (this.frageFragebogen == null) {
			this.frageFragebogen = new ArrayList<Frage_Fragebogen>();
		}

		f.setFragebogen(this);
		this.frageFragebogen.add(f);
	}

	@ManyToOne(targetEntity = Perspektive.class)
	public Perspektive getPerspektive() {
		return perspektive;
	}

	public void setPerspektive(Perspektive perspektive) {
		this.perspektive = perspektive;
	}

	@ManyToOne(targetEntity = Eigenschaft.class)
	public Eigenschaft getEigenschaft() {
		return eigenschaft;
	}

	public void setEigenschaft(Eigenschaft eigenschaft) {
		this.eigenschaft = eigenschaft;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getErstellungsDatum() {
		return erstellungsDatum;
	}

	public void setErstellungsDatum(Date erstellungsDatum) {
		this.erstellungsDatum = erstellungsDatum;
	}

	public Boolean getExportiert() {
		return exportiert;
	}

	public void setExportiert(Boolean exportiert) {
		this.exportiert = exportiert;
	}

}
