package de.hscoburg.evelin.secat.dao.entity.base;

import javax.persistence.MappedSuperclass;

/**
 * ABstracte Klasse zur grundlegenden Beschreibung von Datensaetzen die als Stammdaten klassifiziert sind.
 * 
 * @author zuch1000
 * 
 */
@MappedSuperclass
public abstract class StammdatenEntity extends BaseEntity {

	/**
	 * instance der default version UID
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
