package de.hscoburg.evelin.secat.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class TreeItemWrapper {

	private Handlungsfeld h;
	private Item i;
	private String n;
	private String skala;
	private boolean isHandlungsfeld;

	public TreeItemWrapper(Handlungsfeld ha) {
		this.h = ha;
		this.n = ha.getName();
		this.isHandlungsfeld = true;

	}

	public TreeItemWrapper(Item it) {
		this.i = it;
		this.n = it.getName();
		this.isHandlungsfeld = false;
		if (it.getSkala() != null) {
			this.skala = it.getSkala().getName();
		}
	}

	public Handlungsfeld getHandlungsfeld() {
		return h;
	}

	public Item getItem() {
		return i;
	}

	public String getName() {
		return n;
	}

	public String getSkala() {
		return skala;
	}

	public String getNotiz() {
		if (this.isHandlungsfeld)
			return "";
		else
			return i.getNotiz();
	}

	public boolean isHandlungsfeld() {

		return isHandlungsfeld;
	}

	public List<Eigenschaft> getEigenschaften() {
		if (this.isHandlungsfeld) {
			return new ArrayList<Eigenschaft>();
		} else {
			return i.getEigenschaften();
		}
	}

	public List<Perspektive> getPerspektiven() {
		if (this.isHandlungsfeld) {
			return new ArrayList<Perspektive>();
		} else {
			return i.getPerspektiven();
		}
	}

	public boolean isAktive() {
		if (this.isHandlungsfeld) {
			return this.h.isAktiv();
		} else {
			return this.i.isAktiv();
		}
	}
}
