package de.hscoburg.evelin.secat.test.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.hscoburg.evelin.secat.dao.HandlungsfeldDAO;
import de.hscoburg.evelin.secat.dao.entity.Handlungsfeld;
import de.hscoburg.evelin.secat.test.mock.JPALayerMock;

/**
 * Test fuer den DAO layer.
 * 
 * @author zuch1000
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPALayerMock.class })
@Transactional(propagation = Propagation.REQUIRED)
public class HandlungsfeldDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private HandlungsfeldDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Handlungsfeld handlungsfeld1;

	private Handlungsfeld handlungsfeld2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		handlungsfeld1 = new Handlungsfeld();
		handlungsfeld2 = new Handlungsfeld();

		handlungsfeld1.setId(1);
		handlungsfeld1.setName("test");

		handlungsfeld2.setId(2);
		handlungsfeld2.setName("test2");

		em.persist(handlungsfeld1);

		em.persist(handlungsfeld2);

	}

	@Test
	public void findAll() {

		List<Handlungsfeld> result = dao.findAll();

		Assert.assertTrue(result.contains(handlungsfeld1));
		Assert.assertTrue(result.contains(handlungsfeld2));
	}

	@Test
	public void persist() {
		Handlungsfeld e = new Handlungsfeld();
		e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(3).getId() == 3);
	}

}
