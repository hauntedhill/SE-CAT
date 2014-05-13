package de.hscoburg.evelin.secat.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;
import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

@Entity
public class Lehrveranstaltung extends StammdatenEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8711293069943587392L;
	private Date jahr;

	private Fach fach;

	private SemesterType semester;

	private String dozent;

	private List<Fragebogen> frageboegen;

	@Temporal(TemporalType.DATE)
	public Date getJahr() {
		return jahr;
	}

	public void setJahr(Date jahr) {
		this.jahr = jahr;
	}

	@ManyToOne(targetEntity = Fach.class)
	public Fach getFach() {
		return fach;
	}

	public void setFach(Fach fach) {
		this.fach = fach;
	}

	@OneToMany(targetEntity = Fragebogen.class, mappedBy = "lehrveranstaltung")
	public List<Fragebogen> getFrageboegen() {
		return frageboegen;
	}

	public void setFrageboegen(List<Fragebogen> frageboegen) {
		this.frageboegen = frageboegen;
	}

	@Enumerated
	public SemesterType getSemester() {
		return semester;
	}

	public void setSemester(SemesterType semester) {
		this.semester = semester;
	}

	public String getDozent() {
		return dozent;
	}

	public void setDozent(String dozent) {
		this.dozent = dozent;
	}

}
