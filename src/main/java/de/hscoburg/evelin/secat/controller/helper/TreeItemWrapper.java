package de.hscoburg.evelin.secat.controller.helper;

import java.util.ArrayList;
import java.util.List;

import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

/**
 * Hilfklasse zur Anzeige der Stammdaten
 * 
 * @author moro1000
 * 
 */
public class TreeItemWrapper {

	private Handlungsfeld h;
	private Item i;
	private Bereich b;
	private String n;
	// private String skala;
	private boolean isHandlungsfeld;
	private boolean isItem;
	private boolean isBereich;

	public TreeItemWrapper(Handlungsfeld ha) {
		this.h = ha;
		this.n = ha.getName();
		this.isHandlungsfeld = true;
		this.isItem = false;
		this.isBereich = false;

	}

	public TreeItemWrapper(Item it) {
		this.i = it;
		this.n = it.getName();
		this.isHandlungsfeld = false;
		this.isItem = true;
		this.isBereich = false;
	}

	public TreeItemWrapper(Bereich b) {
		this.b = b;
		this.n = b.getName();
		this.isHandlungsfeld = false;
		this.isItem = false;
		this.isBereich = true;
	}

	public Handlungsfeld getHandlungsfeld() {
		return h;
	}

	public Item getItem() {
		return i;
	}

	public Bereich getBereich() {
		return b;
	}

	public String getName() {
		return n;
	}

	// public String getSkala() {
	// return skala;
	// }

	public String getNotiz() {

		if (this.isItem) {
			return i.getNotiz();
		} else {
			return "";
		}
	}

	public boolean isHandlungsfeld() {

		return isHandlungsfeld;
	}

	public boolean isItem() {

		return isItem;
	}

	public boolean isBereich() {

		return isBereich;
	}

	public List<Eigenschaft> getEigenschaften() {
		if (this.isHandlungsfeld || this.isBereich) {
			return new ArrayList<Eigenschaft>();
		} else {
			return i.getEigenschaften();
		}
	}

	public List<Perspektive> getPerspektiven() {
		if (this.isHandlungsfeld || this.isBereich) {
			return new ArrayList<Perspektive>();
		} else {
			return i.getPerspektiven();
		}
	}

	public boolean isAktive() {
		if (this.isHandlungsfeld) {
			return this.h.isAktiv();
		} else if (this.isItem) {
			return this.i.isAktiv();
		} else {
			return true;
		}
	}
}
