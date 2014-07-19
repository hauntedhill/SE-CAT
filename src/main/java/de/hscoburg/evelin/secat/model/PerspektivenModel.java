package de.hscoburg.evelin.secat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

/**
 * Model zur Verwaltung von Perspektiven
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class PerspektivenModel {

	@Autowired
	private PerspektiveDAO perspektivenDAO;

	/**
	 * Gibt alle Perspektiven zurueck.
	 * 
	 * @return {@link List} mit {@link Perspektive}n
	 */
	public List<Perspektive> getPerspektiven() {
		return perspektivenDAO.findAll();
	}

	/**
	 * Speichert eine Perspektive innerhalb des Systems
	 * 
	 * @param name
	 *            {@link String} mit dem Namen der Perspektive
	 * @throws IllegalArgumentException
	 *             Bei fehlerhaften Eingabewerten oder gesperrter Entity
	 */
	public void savePerspektive(String name) throws IllegalArgumentException {
		if (!"".equals(name.trim())) {
			Perspektive e = new Perspektive();
			e.setName(name);
			perspektivenDAO.persist(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Aktualisiert die uebergebene Perspektive
	 * 
	 * @param e
	 *            {@link Perspektive}
	 * @throws IllegalArgumentException
	 *             Bei fehlerhaften Daten oder wenn Entity gesperrt ist.
	 */
	public void updatePerspektive(Perspektive e) throws IllegalArgumentException {
		if (!"".equals(e.getName()) && !isLocked(e)) {

			perspektivenDAO.merge(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Prueft ob eine Perspektive editiert werden darf.
	 * 
	 * @param e
	 *            {@link Perspektive}
	 * @return true/false
	 */
	public boolean isLocked(Perspektive e) {
		e = perspektivenDAO.findById(e.getId());

		for (Fragebogen f : e.getFrageboegen() != null ? e.getFrageboegen() : new ArrayList<Fragebogen>()) {
			if (f.getExportiertQuestorPro()) {
				return true;
			}
		}

		for (Item i : e.getItems() != null ? e.getItems() : new ArrayList<Item>()) {
			for (Fragebogen f : i.getFrageboegen() != null ? i.getFrageboegen() : new ArrayList<Fragebogen>()) {
				if (f.getExportiertQuestorPro()) {
					return true;
				}
			}
		}

		return false;
	}

}
