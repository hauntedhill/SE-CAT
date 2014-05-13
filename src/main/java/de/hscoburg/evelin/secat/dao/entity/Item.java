package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

@Entity
public class Item extends StammdatenEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1592944199003666766L;

	private String name;

	private String notiz;

	private Handlungsfeld handlungsfeld;

	private List<Eigenschaft> eigenschaften;

	private List<Perspektive> perspektiven;

	private List<Bewertung> bewertungen;

	private List<Fragebogen> frageboegen;

	// private Skala skala;

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

	@ManyToOne(targetEntity = Handlungsfeld.class)
	public Handlungsfeld getHandlungsfeld() {
		return handlungsfeld;
	}

	public void setHandlungsfeld(Handlungsfeld handlungsfeld) {
		this.handlungsfeld = handlungsfeld;
	}

	@ManyToMany(targetEntity = Eigenschaft.class, fetch = FetchType.EAGER)
	public List<Eigenschaft> getEigenschaften() {
		return eigenschaften;
	}

	public void setEigenschaften(List<Eigenschaft> eigenschaften) {
		this.eigenschaften = eigenschaften;
	}

	@ManyToMany(targetEntity = Perspektive.class, fetch = FetchType.EAGER)
	public List<Perspektive> getPerspektiven() {
		return perspektiven;
	}

	public void setPerspektiven(List<Perspektive> perspektiven) {
		this.perspektiven = perspektiven;
	}

	@OneToMany(targetEntity = Bewertung.class)
	public List<Bewertung> getBewertungen() {
		return bewertungen;
	}

	public void setBewertungen(List<Bewertung> bewertungen) {
		this.bewertungen = bewertungen;
	}

	@ManyToMany(targetEntity = Fragebogen.class)
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
}
