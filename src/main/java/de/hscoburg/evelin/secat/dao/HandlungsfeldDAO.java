package de.hscoburg.evelin.secat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft_;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fach_;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen_;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld_;
import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.Item_;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung_;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Perspektive_;

@Repository
public class HandlungsfeldDAO extends BaseDAO<Handlungsfeld> {

	public HandlungsfeldDAO() {
		super(Handlungsfeld.class);

	}

	public List<Handlungsfeld> getHandlungsfelderBy(Boolean handlungsfeldAktiv, Boolean itemAktiv, Perspektive p, Eigenschaft e, String notizHandlungsfeld,
			String notizItem, Fach f) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<Handlungsfeld> handlungsfeldCriteria = cb.createQuery(Handlungsfeld.class);
		Root<Handlungsfeld> handlungsfeldRoot = handlungsfeldCriteria.from(Handlungsfeld.class);
		// Person.address is an embedded attribute
		Join<Handlungsfeld, Item> itemRoot = handlungsfeldRoot.join(Handlungsfeld_.items, JoinType.LEFT);
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
		if (handlungsfeldAktiv != null) {
			predicates.add(cb.equal(handlungsfeldRoot.get(Handlungsfeld_.aktiv), handlungsfeldAktiv));
		}
		if (itemAktiv != null) {
			predicates.add(cb.or(cb.equal(itemRoot.get(Item_.aktiv), itemAktiv), cb.isNull(itemRoot.get(Item_.id))));
		}
		if (!"".equals(notizHandlungsfeld) && notizHandlungsfeld != null) {
			predicates.add(cb.like(cb.upper(handlungsfeldRoot.get(Handlungsfeld_.notiz)), (notizHandlungsfeld + "%").toUpperCase()));
		}
		if (!"".equals(notizItem) && notizItem != null) {
			predicates.add(cb.like(cb.upper(itemRoot.get(Item_.notiz)), (notizItem + "%").toUpperCase()));
		}

		handlungsfeldCriteria.select(handlungsfeldRoot).distinct(true).where(predicates.toArray(new Predicate[0]));

		TypedQuery<Handlungsfeld> q = em.createQuery(handlungsfeldCriteria);

		return q.getResultList();

	}

}
