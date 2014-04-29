package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

@Entity
public class Eigenschaft extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3353799497049963713L;
	private String name;

	private List<Item> items;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(targetEntity = Item.class)
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
