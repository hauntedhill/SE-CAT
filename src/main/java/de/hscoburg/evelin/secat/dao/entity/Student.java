package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entity repraesentiert einen Studenten in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Student extends BaseEntity {

	private static final long serialVersionUID = -4314439908648758052L;
	private String name;

	private List<Fragebogen> frageboegen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(targetEntity = Fragebogen.class, mappedBy = "studenten")
	public List<Fragebogen> getFrageboegen() {
		return frageboegen;
	}

	public void setFrageboegen(List<Fragebogen> frageboegen) {
		this.frageboegen = frageboegen;
	}

}
