package de.hscoburg.evelin.secat.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.EinstellungDAO;
import de.hscoburg.evelin.secat.dao.entity.Einstellung;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;

@Repository
@Transactional
public class EinstellungModel {

	@Autowired
	private EinstellungDAO einstellungDAO;

	/**
	 * Gibt die Einstellung fuer den Standort zurueck.
	 * 
	 * @return {@link Einstellung}
	 */
	public Einstellung getWertForStandort() {

		return einstellungDAO.findByName(EinstellungenType.STANDORT);

	}

	/**
	 * Speichert oder aktualisiert eine Einstellung
	 * 
	 * @param type
	 *            {@link EinstellungenType}
	 * @param wert
	 *            Neuer Wert
	 */
	public void saveEinstellung(EinstellungenType type, String wert) {
		Einstellung e = einstellungDAO.findByName(type);
		if (e != null) {
			e.setWert(wert);
			einstellungDAO.merge(e);
		} else {
			Einstellung eNew = new Einstellung();
			eNew.setName(type);
			eNew.setWert(wert);
			einstellungDAO.persist(eNew);
		}
	}

}
