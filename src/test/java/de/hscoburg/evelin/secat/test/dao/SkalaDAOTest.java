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

import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Skala;
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
public class SkalaDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private SkalaDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Skala skala1;

	private Skala skala2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		skala1 = new Skala();
		skala2 = new Skala();

		skala1.setId(1);
		skala1.setName("test");

		skala2.setId(2);
		skala2.setName("test2");

		em.persist(skala1);

		em.persist(skala2);

	}

	@Test
	public void findAll() {

		List<Skala> result = dao.findAll();

		Assert.assertTrue(result.contains(skala1));
		Assert.assertTrue(result.contains(skala2));
	}

	@Test
	public void persist() {
		Skala e = new Skala();
		e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(3).getId() == 3);
	}

}
