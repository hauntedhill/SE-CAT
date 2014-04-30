package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

@Entity
public class Skala extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2303425624469317407L;

	private String name;

	private List<Item> items;

	@OneToMany(targetEntity = Item.class, mappedBy = "skala")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addItem(Item i) {
		i.setSkala(this);
		if (items == null) {
			items = new ArrayList<Item>();
		}
		items.add(i);
	}

}
