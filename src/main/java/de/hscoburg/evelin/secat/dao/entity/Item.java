package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

/**
 * Entitie repraesentiert einen Item in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Item extends StammdatenEntity {

	private static final long serialVersionUID = 1592944199003666766L;

	private String name;

	private String notiz;

	private Bereich bereich;

	private List<Eigenschaft> eigenschaften;

	private List<Perspektive> perspektiven;

	private List<Bewertung> bewertungen;

	private List<Fragebogen> frageboegen;

	private String frage;

	// private Skala skala;
	@Column(length = 1024)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	@ManyToOne(targetEntity = Bereich.class)
	public Bereich getBereich() {
		return bereich;
	}

	public void setBereich(Bereich handlungsfeld) {
		this.bereich = handlungsfeld;
	}

	@ManyToMany(targetEntity = Eigenschaft.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Eigenschaft> getEigenschaften() {
		return eigenschaften;
	}

	public void setEigenschaften(List<Eigenschaft> eigenschaften) {
		this.eigenschaften = eigenschaften;
	}

	@ManyToMany(targetEntity = Perspektive.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Perspektive> getPerspektiven() {
		return perspektiven;
	}

	public void setPerspektiven(List<Perspektive> perspektiven) {
		this.perspektiven = perspektiven;
	}

	@OneToMany(targetEntity = Bewertung.class, mappedBy = "item")
	public List<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}

	@ManyToMany(targetEntity = Fragebogen.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Fragebogen> getFrageboegen() {
		return frageboegen;
	}

	public void setFrageboegen(List<Fragebogen> frageboegen) {
		this.frageboegen = frageboegen;
	}

	// @ManyToOne(targetEntity = Skala.class, fetch = FetchType.EAGER)
	// public Skala getSkala() {
	// return skala;
	// }
	//
	// public void setSkala(Skala skala) {
	// this.skala = skala;
	// }

	public void addEigenschaft(Eigenschaft e) {
		if (eigenschaften == null) {
			eigenschaften = new ArrayList<Eigenschaft>();
		}

		eigenschaften.add(e);
	}

	public void addPerspektive(Perspektive e) {
		if (perspektiven == null) {
			perspektiven = new ArrayList<Perspektive>();
		}
		perspektiven.add(e);
	}

	public void addFragebogen(Fragebogen f) {
		if (frageboegen == null) {
			frageboegen = new ArrayList<>();
		}
		frageboegen.add(f);

	}

	@Column(length = 1024)
	public String getFrage() {
		return frage;
	}

	public void setFrage(String frage) {
		this.frage = frage;
	}
}
