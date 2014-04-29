package de.hscoburg.evelin.secat.dao.entity.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StammdatenEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean aktiv;

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

}
