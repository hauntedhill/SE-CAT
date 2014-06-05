package de.hscoburg.evelin.secat.dao.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;

@Entity
public class Skala extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2303425624469317407L;

	private String name;

	// private List<Item> items;

	// @OneToMany(targetEntity = Item.class, mappedBy = "skala")
	// public List<Item> getItems() {
	// return items;
	// }
	//
	// public void setItems(List<Item> items) {
	// this.items = items;
	// }

	private List<Fragebogen> frageboegen;

	private List<Frage> fragen;

	private SkalaType type;

	private Integer rows;

	private Integer steps;

	private Integer weight;

	private Integer optimum;

	private String minText;

	private String maxText;

	private List<String> choices;

	private String otherAnswer;

	private String refuseAnswer;

	// @OneToMany(targetEntity = Fragebogen.class, mappedBy = "skala")
	// public List<Fragebogen> getFrageboegen() {
	// return frageboegen;
	// }
	//
	// public void setItems(List<Fragebogen> frageboegen) {
	// this.frageboegen = frageboegen;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(targetEntity = Frage.class, mappedBy = "skala")
	public List<Frage> getFragen() {
		return fragen;
	}

	public void setFragen(List<Frage> fragen) {
		this.fragen = fragen;
	}

	@OneToMany(targetEntity = Fragebogen.class, mappedBy = "skala")
	public List<Fragebogen> getFrageboegen() {
		return frageboegen;
	}

	public void setFrageboegen(List<Fragebogen> frageboegen) {
		this.frageboegen = frageboegen;
	}

	@Enumerated
	public SkalaType getType() {
		return type;
	}

	public void setType(SkalaType type) {
		this.type = type;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getMinText() {
		return minText;
	}

	public void setMinText(String minText) {
		this.minText = minText;
	}

	public String getMaxText() {
		return maxText;
	}

	public void setMaxText(String maxText) {
		this.maxText = maxText;
	}

	public Integer getOptimum() {
		return optimum;
	}

	public void setOptimum(Integer optimum) {
		this.optimum = optimum;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SELECT)
	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> keys) {
		this.choices = keys;
	}

	public String getOtherAnswer() {
		return otherAnswer;
	}

	public void setOtherAnswer(String defaultAnswer) {
		this.otherAnswer = defaultAnswer;
	}

	public String getRefuseAnswer() {
		return refuseAnswer;
	}

	public void setRefuseAnswer(String refuseAnswer) {
		this.refuseAnswer = refuseAnswer;
	}

}
