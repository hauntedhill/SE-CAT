package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

@Entity
public class Perspektive extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1561076949919552247L;
	private String name;

	private List<Item> items;

	private List<Bewertender> bewertende;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(targetEntity = Item.class, mappedBy = "perspektiven")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@OneToMany(targetEntity = Bewertender.class, mappedBy = "perspektive")
	public List<Bewertender> getBewertende() {
		return bewertende;
	}

	public void setBewertende(List<Bewertender> bewertende) {
		this.bewertende = bewertende;
	}

	public void addItem(Item i) {
		if (items == null) {
			items = new ArrayList<Item>();
		}
		items.add(i);
	}
}
