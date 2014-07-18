package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entity repraesentiert eine Eigenschaft in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Eigenschaft extends BaseEntity {

	private static final long serialVersionUID = -3353799497049963713L;
	private String name;

	private List<Item> items;

	private List<Fragebogen> frageboegen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(targetEntity = Item.class, mappedBy = "eigenschaften")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@OneToMany(targetEntity = Fragebogen.class, mappedBy = "eigenschaft")
	public List<Fragebogen> getFrageboegen() {
		return frageboegen;
	}

	public void setFrageboegen(List<Fragebogen> frageboegen) {
		this.frageboegen = frageboegen;
	}

}
