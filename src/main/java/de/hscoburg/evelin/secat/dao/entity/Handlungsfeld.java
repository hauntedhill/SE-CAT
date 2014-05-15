package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

@Entity
public class Handlungsfeld extends StammdatenEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9082001636109048554L;

	private String name;

	private String notiz;

	private List<Bereich> bereiche;

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

	@OneToMany(mappedBy = "handlungsfeld", targetEntity = Bereich.class, fetch = FetchType.EAGER)
	public List<Bereich> getBereiche() {
		return bereiche;
	}

	public void setBereiche(List<Bereich> items) {
		this.bereiche = items;
	}

	public void addBereich(Bereich i) {
		i.setHandlungsfeld(this);
		if (bereiche == null) {
			bereiche = new ArrayList<Bereich>();
		}
		bereiche.add(i);
	}

}
