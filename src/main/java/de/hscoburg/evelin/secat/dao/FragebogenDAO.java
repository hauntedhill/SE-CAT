package de.hscoburg.evelin.secat.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen_;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
import de.hscoburg.evelin.secat.dao.entity.Skala;

/**
 * DAO zum Zugriff auf Fragebogen Entities
 * 
 * @author zuch1000
 * 
 */
@Repository
public class FragebogenDAO extends BaseDAO<Fragebogen> {

	public FragebogenDAO() {
		super(Fragebogen.class);

	}

	/**
	 * Gibt alle Frageboegen fuer die uebergebenen Parameter zurueck. (null wird ignoriert)
	 * 
	 * @param e
	 *            - {@link Eigenschaft} oder null
	 * @param p
	 *            - {@link Perspektive} oder null
	 * @param l
	 *            - {@link Lehrveranstaltung} oder null
	 * @param name
	 *            - Name des Fragebogens
	 * @param von
	 *            - Von {@link Date}
	 * @param bis
	 *            - Bis {@link Date}
	 * @param s
	 *            - Gesuchte {@link Skala} oder null
	 * @return {@link List} mit gefundenen {@link Fragebogen}
	 */
	public List<Fragebogen> getFrageboegenFor(Eigenschaft e, Perspektive p, Lehrveranstaltung l, String name, Date von, Date bis, Skala s, boolean archiv) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		List<Predicate> predicates = new ArrayList<Predicate>();

		CriteriaQuery<Fragebogen> fragebogenCriteria = cb.createQuery(Fragebogen.class);
		Root<Fragebogen> fragebogenRoot = fragebogenCriteria.from(Fragebogen.class);

		if (e != null) {

			predicates.add(cb.equal(fragebogenRoot.get(Fragebogen_.eigenschaft), e));
		}
		if (p != null) {

			predicates.add(cb.equal(fragebogenRoot.get(Fragebogen_.perspektive), p));
		}

		if (l != null) {

			predicates.add(cb.equal(fragebogenRoot.get(Fragebogen_.lehrveranstaltung), l));
		}

		if (s != null) {

			predicates.add(cb.equal(fragebogenRoot.get(Fragebogen_.skala), s));
		}

		if (von != null && bis != null) {

			predicates.add(cb.between(fragebogenRoot.get(Fragebogen_.erstellungsDatum), von, bis));
		} else if (von != null) {

			predicates.add(cb.greaterThanOrEqualTo(fragebogenRoot.get(Fragebogen_.erstellungsDatum), von));
		} else if (bis != null) {

			predicates.add(cb.lessThanOrEqualTo(fragebogenRoot.get(Fragebogen_.erstellungsDatum), bis));
		}

		if (!"".equals(name) && name != null) {
			predicates.add(cb.like(cb.upper(fragebogenRoot.get(Fragebogen_.name)), (name + "%").toUpperCase()));
		}

		predicates.add(cb.equal(fragebogenRoot.get(Fragebogen_.archiviert), archiv));

		fragebogenCriteria.select(fragebogenRoot).where(predicates.toArray(new Predicate[0]));

		TypedQuery<Fragebogen> q = em.createQuery(fragebogenCriteria);

		return q.getResultList();

	}

}
