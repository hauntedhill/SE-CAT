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

	private List<Item> items;

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

	@OneToMany(mappedBy = "handlungsfeld", targetEntity = Item.class, fetch = FetchType.EAGER)
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item i) {
		i.setHandlungsfeld(this);
		if (items == null) {
			items = new ArrayList<Item>();
		}
		items.add(i);
	}

}
