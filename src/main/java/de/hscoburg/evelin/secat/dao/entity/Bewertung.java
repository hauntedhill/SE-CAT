package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

@Entity
public class Bewertung extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994796060198831594L;

	private String wert;

	private Bewertender bewertender;

	private Item item;

	private Fragebogen fragebogen;

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

}
