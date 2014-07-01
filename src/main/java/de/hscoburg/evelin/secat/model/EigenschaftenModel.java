package de.hscoburg.evelin.secat.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Item;

/**
 * Model zur Verarbeitung von Eigenschaften
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class EigenschaftenModel {

	@Autowired
	private EigenschaftenDAO eigenschaftenDAO;

	/**
	 * Gibt alle im System vorhandenen Eigenschaften zurueck.
	 * 
	 * @return {@link List} mit {@link Eigenschaft}en
	 */
	public List<Eigenschaft> getEigenschaften() {
		return eigenschaftenDAO.findAll();
	}

	/**
	 * Speichert eine neue {@link Eigenschaft} im System
	 * 
	 * @param name
	 *            - {@link String} mit dem Namen
	 * @throws IllegalArgumentException
	 *             Wenn der Name ungueltig ist
	 */
	public void saveEigenschaft(String name) throws IllegalArgumentException {
		if (!"".equals(name.trim())) {
			Eigenschaft e = new Eigenschaft();
			e.setName(name);
			eigenschaftenDAO.persist(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void updateEigenschaft(Eigenschaft e) throws IllegalArgumentException {
		if (!"".equals(e.getName()) && !isLocked(e)) {

			eigenschaftenDAO.merge(e);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public boolean isLocked(Eigenschaft e) {
		e = eigenschaftenDAO.findById(e.getId());

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
