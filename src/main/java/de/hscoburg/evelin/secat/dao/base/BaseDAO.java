package de.hscoburg.evelin.secat.dao.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

/**
 * ABstrakte-Klasse zur Definition des Basisverhaltens eines DAOs
 * 
 * @author zuch1000
 * 
 * @param <T>
 *            Type der zu verwaltenden Entity
 */
public abstract class BaseDAO<T extends BaseEntity> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	public BaseDAO(Class<T> type) {
		this.type = type;
	}

	/**
	 * Speichert das Objekt in der DB und setzt ID
	 * 
	 * @param o
	 *            Das zu speichernde Objekt
	 */
	public void persist(T o) {
		em.persist(o);
		em.flush();
	}

	/**
	 * Aktualisiert das Objekt bzw. attached es mit der DB
	 * 
	 * @param o
	 *            Das zu aktualisierende Objekt
	 */
	public void merge(T o) {
		em.merge(o);
	}

	/**
	 * Loescht ein Objekt aus der DB
	 * 
	 * @param o
	 *            Das zu loeschende Objekt
	 */
	public void remove(T o) {
		em.remove(o);
	}

	/**
	 * Holt die Entitie mit der entsprechenden ID
	 * 
	 * @param id
	 *            ID des Objektes
	 * @return T Das gefundene Objekt
	 */
	public T findById(Integer id) {
		return em.find(type, id);
	}

	/**
	 * Gibt alle Entities des Typs zurueck.
	 * 
	 * @return {@link List} mit T
	 */
	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<T> criteria = cb.createQuery(type);
		Root<T> root = criteria.from(type);
		// Person.address is an embedded attribute

		criteria.select(root);

		TypedQuery<T> q = em.createQuery(criteria);

		return q.getResultList();

	}

}
