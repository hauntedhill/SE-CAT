package de.hscoburg.evelin.secat.dao;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Einstellung;
import de.hscoburg.evelin.secat.dao.entity.Einstellung_;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;

@Repository
public class EinstellungDAO extends BaseDAO<Einstellung> {

	public EinstellungDAO() {
		super(Einstellung.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sucht eine Einstellung anhand dessen eindeutigen Namen
	 * 
	 * @param name
	 *            {@link EinstellungenType}
	 * @return Die gefundene {@link Einstellung} oder null
	 */
	public Einstellung findByName(de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType name) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		// List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<Einstellung> einstellungenCriteria = cb.createQuery(Einstellung.class);
		Root<Einstellung> einstellungenRoot = einstellungenCriteria.from(Einstellung.class);

		einstellungenCriteria.select(einstellungenRoot).distinct(true).where(cb.equal(einstellungenRoot.get(Einstellung_.name), name));

		TypedQuery<Einstellung> q = em.createQuery(einstellungenCriteria);

		return q.getResultList().size() == 1 ? q.getResultList().get(0) : null;

	}

}
