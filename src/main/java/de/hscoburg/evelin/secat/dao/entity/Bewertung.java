package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entitie repraesentiert einen Bewertung in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Bewertung extends BaseEntity {

	private static final long serialVersionUID = -5994796060198831594L;

	private String wert;

	private Bewertender bewertender;

	private Item item;

	private Fragebogen fragebogen;

	private Frage frage;

	private String welle;

	private String zeilenid;

	private String quelle;

	private String zeit;

	private Boolean ausreiser;

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	@ManyToOne(targetEntity = Bewertender.class)
	public Bewertender getBewertender() {
		return bewertender;
	}

	public void setBewertender(Bewertender bewertender) {
		this.bewertender = bewertender;
	}

	@ManyToOne(targetEntity = Item.class)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(targetEntity = Fragebogen.class)
	public Fragebogen getFragebogen() {
		return fragebogen;
	}

	public void setFragebogen(Fragebogen fragebogen) {
		this.fragebogen = fragebogen;
	}

	@ManyToOne(targetEntity = Frage.class)
	public Frage getFrage() {
		return frage;
	}

	public void setFrage(Frage frage) {
		this.frage = frage;
	}

	public String getWelle() {
		return welle;
	}

	public void setWelle(String welle) {
		this.welle = welle;
	}

	public String getZeilenid() {
		return zeilenid;
	}

	public void setZeilenid(String rawid) {
		this.zeilenid = rawid;
	}

	public String getQuelle() {
		return quelle;
	}

	public void setQuelle(String source) {
		this.quelle = source;
	}

	public String getZeit() {
		return zeit;
	}

	public void setZeit(String zeit) {
		this.zeit = zeit;
	}

	public Boolean getAusreiser() {
		return ausreiser;
	}

	public void setAusreiser(Boolean ausreiser) {
		this.ausreiser = ausreiser;
	}

}
