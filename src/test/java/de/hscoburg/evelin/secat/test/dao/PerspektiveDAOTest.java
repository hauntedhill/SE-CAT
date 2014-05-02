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

import de.hscoburg.evelin.secat.dao.PerspektiveDAO;
import de.hscoburg.evelin.secat.dao.entity.Perspektive;
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
public class PerspektiveDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private PerspektiveDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Perspektive perspektive1;

	private Perspektive perspektive2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		perspektive1 = new Perspektive();
		perspektive2 = new Perspektive();

		perspektive1.setId(1);
		perspektive1.setName("test");

		perspektive2.setId(2);
		perspektive2.setName("test2");

		em.persist(perspektive1);

		em.persist(perspektive2);

	}

	@Test
	public void findAll() {

		List<Perspektive> result = dao.findAll();

		Assert.assertTrue(result.contains(perspektive1));
		Assert.assertTrue(result.contains(perspektive2));
	}

	@Test
	public void persist() {
		Perspektive e = new Perspektive();
		e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(3).getId() == 3);
	}

}
