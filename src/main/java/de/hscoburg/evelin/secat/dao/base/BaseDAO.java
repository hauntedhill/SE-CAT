package de.hscoburg.evelin.secat.dao.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;

public abstract class BaseDAO<T extends BaseEntity> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	public BaseDAO(Class<T> type) {
		this.type = type;
	}

	public void persist(T o) {
		em.persist(o);
		em.flush();
	}

	public void merge(T o) {
		em.merge(o);
	}

	public void remove(T o) {
		em.remove(o);
	}

	public T findById(Integer id) {
		return em.find(type, id);
	}

	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<T> criteria = cb.createQuery(type);
		Root<T> root = criteria.from(type);
		// Person.address is an embedded attribute

		criteria.select(root);

		TypedQuery<T> q = em.createQuery(criteria);
		System.out.println("");
		return q.getResultList();

	}

}
