package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;

/**
 * Entitie repraesentiert eine Skala in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Skala extends BaseEntity {

	private static final long serialVersionUID = 2303425624469317407L;

	private String name;

	private List<Fragebogen> frageboegen;

	private List<Frage> fragen;

	private SkalaType type;

	private Integer zeilen;

	private Integer schritte;

	private Integer schrittWeite;

	private Integer optimum;

	private String minText;

	private String maxText;

	private List<String> auswahl;

	private String andereAntwort;

	private String verweigerungsAntwort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(targetEntity = Frage.class, mappedBy = "skala")
	public List<Frage> getFragen() {
		return fragen;
	}

	public void setFragen(List<Frage> fragen) {
		this.fragen = fragen;
	}

	@OneToMany(targetEntity = Fragebogen.class, mappedBy = "skala")
	public List<Fragebogen> getFrageboegen() {
		return frageboegen;
	}

	public void setFrageboegen(List<Fragebogen> frageboegen) {
		this.frageboegen = frageboegen;
	}

	@Enumerated
	public SkalaType getType() {
		return type;
	}

	public void setType(SkalaType type) {
		this.type = type;
	}

	public Integer getZeilen() {
		return zeilen;
	}

	public void setZeilen(Integer rows) {
		this.zeilen = rows;
	}

	public Integer getSchritte() {
		return schritte;
	}

	public void setSchritte(Integer steps) {
		this.schritte = steps;
	}

	public Integer getSchrittWeite() {
		return schrittWeite;
	}

	public void setSchrittWeite(Integer weight) {
		this.schrittWeite = weight;
	}

	public String getMinText() {
		return minText;
	}

	public void setMinText(String minText) {
		this.minText = minText;
	}

	public String getMaxText() {
		return maxText;
	}

	public void setMaxText(String maxText) {
		this.maxText = maxText;
	}

	public Integer getOptimum() {
		return optimum;
	}

	public void setOptimum(Integer optimum) {
		this.optimum = optimum;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SELECT)
	public List<String> getAuswahl() {
		return auswahl;
	}

	public void setAuswahl(List<String> keys) {
		this.auswahl = keys;
	}

	public String getAndereAntwort() {
		return andereAntwort;
	}

	public void setAndereAntwort(String defaultAnswer) {
		this.andereAntwort = defaultAnswer;
	}

	public String getVerweigerungsAntwort() {
		return verweigerungsAntwort;
	}

	public void setVerweigerungsAntwort(String refuseAnswer) {
		this.verweigerungsAntwort = refuseAnswer;
	}

}
