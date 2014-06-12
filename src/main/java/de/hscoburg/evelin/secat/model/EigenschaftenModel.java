package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;

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

}
