package de.hscoburg.evelin.secat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Bereich;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft_;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fach_;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen_;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Item_;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung_;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Perspektive_;

/**
 * DAO zum Zugriff auf Item Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class ItemDAO extends BaseDAO<Item> {

	public ItemDAO() {
		super(Item.class);

	}

	/**
	 * Gibt eine Liste mit allen Bereichen zurueck, die den Kriterien entsprechen
	 * 
	 * @param h
	 *            Der {@link Bereich} des Items
	 * @param itemAktiv
	 *            ItemAktiv {@link Boolean} der Items des Bereiches
	 * @param p
	 *            {@link Perspektive} der Items
	 * @param e
	 *            {@link Eigenschaft} der Items
	 * @param notizHandlungsfeld
	 *            notiz des Handlungsfeldes
	 * @param notizItem
	 *            notiz des Items
	 * @param f
	 *            {@link Fach} des Items des Bereiches
	 * @return {@link List} mit gefundenen {@link Bereich}
	 */
	public List<Item> getItemBy(Bereich h, Boolean itemAktiv, Perspektive p, Eigenschaft e, String notizHandlungsfeld, String notizItem, Fach f) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<Item> itemCriteria = cb.createQuery(Item.class);
		Root<Item> itemRoot = itemCriteria.from(Item.class);
		// Person.address is an embedded attribute
		// Address.country is a ManyToOne
		if (e != null) {
			Join<Item, Eigenschaft> eigenschaftRoot = itemRoot.join(Item_.eigenschaften);
			predicates.add(cb.equal(eigenschaftRoot.get(Eigenschaft_.id), e.getId()));
		}
		if (p != null) {
			Join<Item, Perspektive> perspektiveRoot = itemRoot.join(Item_.perspektiven);
			predicates.add(cb.equal(perspektiveRoot.get(Perspektive_.id), p.getId()));
		}

		if (f != null) {
			Join<Item, Fragebogen> frageogenRoot = itemRoot.join(Item_.frageboegen);
			Join<Fragebogen, Lehrveranstaltung> leherveranstaltungRoot = frageogenRoot.join(Fragebogen_.lehrveranstaltung);
			Join<Lehrveranstaltung, Fach> fachRoot = leherveranstaltungRoot.join(Lehrveranstaltung_.fach);
			predicates.add(cb.equal(fachRoot.get(Fach_.id), f.getId()));
		}
		if (itemAktiv != null) {
			predicates.add(cb.equal(itemRoot.get(Item_.aktiv), itemAktiv));
		}
		predicates.add(cb.equal(itemRoot.get(Item_.bereich), h));

		if (!"".equals(notizItem) && notizItem != null) {
			predicates.add(cb.like(cb.upper(itemRoot.get(Item_.notiz)), (notizItem + "%").toUpperCase()));
		}

		itemCriteria.select(itemRoot).distinct(true).where(predicates.toArray(new Predicate[0]));

		TypedQuery<Item> q = em.createQuery(itemCriteria);

		return q.getResultList();
	}

}
