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

import de.hscoburg.evelin.secat.dao.EinstellungDAO;
import de.hscoburg.evelin.secat.dao.entity.Einstellung;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;
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
public class EinstellungDAOTest {
	/**
	 * Instance der zu testenden Klasse
	 */
	@Autowired
	private EinstellungDAO dao;

	/**
	 * Instance des EntityManagers
	 */
	@PersistenceContext
	protected EntityManager em;

	private Einstellung e1;

	private Einstellung e2;

	/**
	 * Hier muss die Datenbank mit allen Testdaten initialisiert werden
	 */
	@Before
	public void init() {

		e1 = new Einstellung();
		e1.setName(EinstellungenType.STANDORT);
		e2 = new Einstellung();

		em.persist(e1);

		em.persist(e2);
		em.flush();
	}

	@Test
	public void findAll() {

		List<Einstellung> result = dao.findAll();

		Assert.assertTrue(result.contains(e1));
		Assert.assertTrue(result.contains(e2));
	}

	@Test
	public void persist() {
		Einstellung e = new Einstellung();
		// e.setId(3);
		// e.setName("test3");
		dao.persist(e);

		Assert.assertTrue(dao.findById(e.getId()).getId() == e.getId());
	}

	@Test
	public void getEinstellung() {
		Einstellung result = dao.findByName(EinstellungenType.STANDORT);
		Assert.assertTrue(e1.equals(result));

		em.remove(e1);
		em.flush();

		result = dao.findByName(EinstellungenType.STANDORT);
		Assert.assertTrue(result == null);
	}

}
