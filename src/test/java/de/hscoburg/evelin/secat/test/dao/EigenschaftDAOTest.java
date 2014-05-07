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

import de.hscoburg.evelin.secat.dao.EigenschaftenDAO;
import de.hscoburg.evelin.secat.dao.entity.Eigenschaft;
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
public class EigenschaftDAOTest {

	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private EigenschaftenDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Eigenschaft eigenschaft1;

	private Eigenschaft eigenschaft2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		eigenschaft1 = new Eigenschaft();
		eigenschaft2 = new Eigenschaft();

		// eigenschaft1.setId(1);
		eigenschaft1.setName("test");

		// eigenschaft2.setId(2);
		eigenschaft2.setName("test2");

		em.persist(eigenschaft1);

		em.persist(eigenschaft2);
		em.flush();
	}

	@Test
	public void findAll() {

		List<Eigenschaft> result = dao.findAll();

		Assert.assertTrue(result.contains(eigenschaft1));
		Assert.assertTrue(result.contains(eigenschaft2));
	}

	@Test
	public void persist() {
		Eigenschaft e = new Eigenschaft();
		// e.setId(3);
		e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(e.getId()).getId() == e.getId());
	}

}
