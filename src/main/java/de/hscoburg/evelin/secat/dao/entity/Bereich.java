package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * Entity repraesentiert einen Bereich in der Datenbank
 * 
 * @author zuch1000
 * 
 */
@Entity
public class Bereich extends BaseEntity {

	private static final long serialVersionUID = -4107832969718646738L;

	private Handlungsfeld handlungsfeld;

	private String name;

	private List<Item> items;

	@OneToMany(mappedBy = "bereich", targetEntity = Item.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@ManyToOne(targetEntity = Handlungsfeld.class)
	public Handlungsfeld getHandlungsfeld() {
		return handlungsfeld;
	}

	public void setHandlungsfeld(Handlungsfeld handlungsfeld) {
		this.handlungsfeld = handlungsfeld;
	}

	@Column(length = 1024)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addItem(Item i) {
		if (items == null) {
			items = new ArrayList<Item>();
		}
		i.setBereich(this);
		items.add(i);
	}

}
