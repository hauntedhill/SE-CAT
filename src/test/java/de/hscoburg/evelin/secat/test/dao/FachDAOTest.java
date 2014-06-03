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

import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
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
public class FachDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private FachDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Fach fach1;

	private Fach fach2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		fach1 = new Fach();
		fach2 = new Fach();

		// fach1.setId(1);
		fach1.setName("test");

		// fach2.setId(2);
		fach2.setName("test2");

		em.persist(fach1);

		em.persist(fach2);
		em.flush();

	}

	@Test
	public void findAll() {

		List<Fach> result = dao.findAll();

		Assert.assertTrue(result.contains(fach1));
		Assert.assertTrue(result.contains(fach2));
	}

	@Test
	public void persist() {
		Fach e = new Fach();
		// e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(e.getId()).getId() == e.getId());
	}

}
