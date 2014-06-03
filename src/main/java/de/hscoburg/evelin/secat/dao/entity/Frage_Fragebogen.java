package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.FragePosition;

@Entity
public class Frage_Fragebogen extends BaseEntity {

	private Fragebogen fragebogen;

	private Frage frage;

	private FragePosition position;

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

	@Enumerated
	public FragePosition getPosition() {
		return position;
	}

	public void setPosition(FragePosition position) {
		this.position = position;
	}

}
