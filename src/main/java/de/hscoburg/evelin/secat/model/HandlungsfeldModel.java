package de.hscoburg.evelin.secat.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.BereichDAO;
import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.ItemDAO;
import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;

/**
 * Klasse zur Verwaltung von Handlungsfeldern und Items
 * 
 * @author zuch1000
 * 
 */
@Repository
@Transactional
public class HandlungsfeldModel {

	@Autowired
	private HandlungsfeldDAO handlungsfeldDAO;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private EigenschaftenDAO eigenschaftenDAO;
	@Autowired
	private PerspektiveDAO perspektiveDAO;
	@Autowired
	private SkalaDAO skalaDAO;
	@Autowired
	private FachDAO fachDAO;

	@Autowired
	private BereichDAO bereichDAO;

	/**
	 * Gibt alle Items fuer einen Bereich zurueck, null values werden ignoriert.
	 * 
	 * @param h
	 *            Der {@link Bereich}
	 * @param itemAktiv
	 *            {@link Boolean} ob itemAKtiv ist oder nicht
	 * @param p
	 *            {@link Perspektive} des Items
	 * @param e
	 *            {@link Eigenschaft} des Items
	 * @param notizHandlungsfeld
	 *            Notiz des Handlungsfeldes
	 * @param notizItem
	 *            Notiz des Items
	 * @param f
	 *            {@link Fach} des Items
	 * @return {@link List} mit {@link Item}s
	 */
	public List<Item> getItemBy(Bereich h, Boolean itemAktiv, Perspektive p, Eigenschaft e, String notizHandlungsfeld, String notizItem, Fach f) {
		return itemDAO.getItemBy(h, itemAktiv, p, e, notizHandlungsfeld, notizItem, f);
	}

	/**
	 * Gibt alle Bereiche fuer einen Handlungsfeld zurueck, null values werden ignoriert.
	 * 
	 * @param h
	 *            Das {@link Handlungsfeld}
	 * @param itemAktiv
	 *            {@link Boolean} ob itemAKtiv ist oder nicht
	 * @param p
	 *            {@link Perspektive} des Items
	 * @param e
	 *            {@link Eigenschaft} des Items
	 * @param notizHandlungsfeld
	 *            - Notiz des Handlungsfeldes
	 * @param notizItem
	 *            Notiz des Items
	 * @param f
	 *            {@link Fach} des Items
	 * @return {@link List} mit {@link Bereich}en
	 */
	public List<Bereich> getBereichBy(Handlungsfeld h, Boolean itemAktiv, Perspektive p, Eigenschaft e, String notizHandlungsfeld, String notizItem, Fach f) {
		return bereichDAO.getBereicheBy(h, itemAktiv, p, e, notizHandlungsfeld, notizItem, f);
	}

	/**
	 * Gibt Handlungsfelder anhand der aktiv KZs zurueck.
	 * 
	 * @param handlungsfeldAktiv
	 *            {@link Handlungsfeld} Aktiv
	 * @param itemAktiv
	 *            {@link Item} Aktiv
	 * @return {@link List} mit {@link Handlungsfeld}ern
	 */
	public List<Handlungsfeld> getHandlungsfelderBy(Boolean handlungsfeldAktiv, Boolean itemAktiv) {
		return getHandlungsfelderBy(handlungsfeldAktiv, itemAktiv, null, null, null, null, null);
	}

	/**
	 * Gibt alleHandlungsfelder zurueck, null values werden ignoriert.
	 * 
	 * @param handlungsfeldAktiv
	 *            Gibt an ob {@link Handlungsfeld} Aktiv sein soll
	 * @param itemAktiv
	 *            {@link Boolean} ob itemAKtiv ist oder nicht
	 * @param p
	 *            {@link Perspektive} des Items
	 * @param e
	 *            {@link Eigenschaft} des Items
	 * @param notizHandlungsfeld
	 *            Notiz des Handlungsfeldes
	 * @param notizItem
	 *            Notiz des Items
	 * @param f
	 *            {@link Fach} des Items
	 * @return {@link List} mit {@link Handlungsfeld}ern
	 */
	public List<Handlungsfeld> getHandlungsfelderBy(Boolean handlungsfeldAktiv, Boolean itemAktiv, Perspektive p, Eigenschaft e, String notizHandlungsfeld,
			String notizItem, Fach f) {

		return handlungsfeldDAO.getHandlungsfelderBy(handlungsfeldAktiv, itemAktiv, p, e, notizHandlungsfeld, notizItem, f);
	}

	@Deprecated
	public void persistHandlungsfeld(Handlungsfeld h) {
		handlungsfeldDAO.persist(h);
	}

	@Deprecated
	public void mergeHandlugsfeld(Handlungsfeld h) {
		handlungsfeldDAO.merge(h);
	}

	@Deprecated
	public void persistItem(Item i) {
		itemDAO.persist(i);
	}

	@Deprecated
	public void mergeItem(Item i) {
		itemDAO.merge(i);
	}

	@Deprecated
	public void persistBereich(Bereich b) {
		bereichDAO.persist(b);
	}

	@Deprecated
	public void mergeBereich(Bereich b) {
		bereichDAO.merge(b);
	}

	/**
	 * Sucht ein Handlungsfeld anhand dessen eindeutiger ID
	 * 
	 * @param id
	 *            {@link Integer} mit der ID
	 * @return Das gefundene {@link Handlungsfeld} oder null
	 */
	public Handlungsfeld findHandlungsfeldById(int id) {
		return handlungsfeldDAO.findById(id);
	}

}
