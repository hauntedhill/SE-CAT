package de.hscoburg.evelin.secat.test.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.SkalaDAO;
import de.hscoburg.evelin.secat.dao.entity.Frage;
import de.hscoburg.evelin.secat.dao.entity.Frage_Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Skala;
import de.hscoburg.evelin.secat.dao.entity.base.SkalaType;
import de.hscoburg.evelin.secat.model.SkalenModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;

public class SkalenModelTest extends BaseModelTest {

	private static SkalenModel model = new SkalenModel();

	private static SkalaDAO skalaDAO;

	@Test(expected = IllegalArgumentException.class)
	public void save1() {
		model.saveSkala(null, "123", null, null, null, null, null, null, null, null, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save2() {
		model.saveSkala(SkalaType.DISCRET, "", null, null, null, null, null, null, null, null, null, null);
	}

	@Test
	public void save3() {
		model.saveSkala(SkalaType.FREE, "testFree", "2", null, null, null, null, null, null, null, null, null);
		Assert.assertTrue(((Skala) persistObjects.get(0)).getName().equals("testFree"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void save4() {
		model.saveSkala(SkalaType.DISCRET, "testFree", null, "4", "1", "", null, null, null, null, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void save5() {
		model.saveSkala(SkalaType.DISCRET, "testFree", null, "4", "1", "test", "", null, null, null, null, null);
	}

	@Test
	public void save6() {
		model.saveSkala(SkalaType.DISCRET, "testDiscrete", null, "4", "1", "test2", "test1", "1", null, null, null, null);
		Assert.assertTrue(((Skala) persistObjects.get(0)).getName().equals("testDiscrete"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void save7() {
		model.saveSkala(SkalaType.MC, "testMC", null, null, "1", null, null, null, new ArrayList<String>(), null, null, null);
	}

	@Test
	public void save8() {
		model.saveSkala(SkalaType.MC, "testMC", null, null, "1", null, null, null, Arrays.asList(new String[] { "wert1", "wert2" }), "1", null, null);
		Assert.assertTrue(((Skala) persistObjects.get(0)).getName().equals("testMC"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked1() {
		updateSkala2.setName("test1");
		model.updateSkala(updateSkala2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked2() {
		updateSkala3.setName("test1");
		model.updateSkala(updateSkala3);

	}

	@Test(expected = NullPointerException.class)
	public void updateLocked3() {
		updateSkala1.setName("test1");
		model.updateSkala(updateSkala1);

	}

	@Test(expected = NullPointerException.class)
	public void updateLocked4() {
		updateSkala4.setName("test1");
		model.updateSkala(updateSkala4);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked5() {
		Skala s = new Skala();
		s.setId(80);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.FREE);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked6() {
		Skala s = new Skala();
		s.setId(81);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.FREE);
		s.setZeilen(2);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked7() {
		Skala s = new Skala();
		s.setId(82);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.FREE);
		s.setZeilen(2);
		s.setName("");

		model.updateSkala(s);

	}

	@Test
	public void updateLocked8() {
		Skala s = new Skala();
		s.setId(83);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.FREE);
		s.setZeilen(2);
		s.setName("123");

		model.updateSkala(s);
		Assert.assertTrue(((Skala) mergedObjects.get(0)).getName().equals("123"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked9() {
		Skala s = new Skala();
		s.setId(84);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.MC);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked10() {
		Skala s = new Skala();
		s.setId(85);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.MC);
		s.setAuswahl(Arrays.asList(new String[] {}));

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked11() {
		Skala s = new Skala();
		s.setId(86);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.MC);
		s.setAuswahl(Arrays.asList(new String[] { "test" }));

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked12() {
		Skala s = new Skala();
		s.setId(87);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.MC);
		s.setAuswahl(Arrays.asList(new String[] { "test" }));
		s.setSchrittWeite(2);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked13() {
		Skala s = new Skala();
		s.setId(88);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.MC);
		s.setAuswahl(Arrays.asList(new String[] { "test" }));
		s.setSchrittWeite(2);
		s.setName("");
		model.updateSkala(s);

	}

	@Test
	public void updateLocked14() {
		Skala s = new Skala();
		s.setId(89);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.MC);
		s.setAuswahl(Arrays.asList(new String[] { "test" }));
		s.setSchrittWeite(2);
		s.setName("123");
		model.updateSkala(s);
		Assert.assertTrue(((Skala) mergedObjects.get(0)).getName().equals("123"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked15() {
		Skala s = new Skala();
		s.setId(90);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked16() {
		Skala s = new Skala();
		s.setId(91);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked17() {
		Skala s = new Skala();
		s.setId(92);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked18() {
		Skala s = new Skala();
		s.setId(93);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked19() {
		Skala s = new Skala();
		s.setId(94);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);
		s.setMaxText("");

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked20() {
		Skala s = new Skala();
		s.setId(95);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);
		s.setMaxText("123");
		s.setMinText("");

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked21() {
		Skala s = new Skala();
		s.setId(96);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);
		s.setMaxText("123");
		s.setMinText("123");
		s.setName("");

		model.updateSkala(s);

	}

	@Test
	public void updateLocked22() {
		Skala s = new Skala();
		s.setId(97);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);
		s.setMaxText("123");
		s.setMinText("123");
		s.setName("123");

		model.updateSkala(s);

		Assert.assertTrue(((Skala) mergedObjects.get(0)).getName().equals("123"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked23() {
		Skala s = new Skala();
		s.setId(98);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);
		s.setMaxText("");
		s.setMinText("123");

		model.updateSkala(s);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked24() {
		Skala s = new Skala();
		s.setId(99);
		Mockito.when(skalaDAO.findById(s.getId())).thenReturn(s);
		s.setType(SkalaType.DISCRET);
		s.setOptimum(2);
		s.setSchrittWeite(2);
		s.setSchritte(2);
		s.setMaxText("123");
		s.setMinText("123");

		model.updateSkala(s);

	}

	private static Skala updateSkala1;

	private static Skala updateSkala2;

	private static Skala updateSkala3;

	private static Skala updateSkala4;

	@BeforeClass
	public static void setup() throws Exception {
		skalaDAO = Mockito.mock(SkalaDAO.class);

		updateSkala1 = new Skala();
		updateSkala1.setId(1);
		updateSkala1.setName("test");

		Fragebogen f1 = new Fragebogen();
		f1.setExportiertQuestorPro(false);

		Fragebogen f2 = new Fragebogen();
		f2.setExportiertQuestorPro(true);

		updateSkala2 = new Skala();
		updateSkala2.setId(2);
		updateSkala2.setName("asdf");

		// updateSkala2.setItems(Arrays.asList(new Item[] { new Item() }));

		updateSkala2.setFrageboegen((Arrays.asList(new Fragebogen[] { f1, f2 })));

		updateSkala3 = new Skala();
		updateSkala3.setId(3);
		updateSkala3.setName("asdf");

		Frage i1 = new Frage();
		Frage_Fragebogen ff = new Frage_Fragebogen();
		Frage i2 = new Frage();
		Frage_Fragebogen ff2 = new Frage_Fragebogen();

		ff.setFrage(i1);
		ff2.setFrage(i2);
		ff.setFragebogen(f1);
		ff2.setFragebogen(f2);

		i1.setFrageFragebogen(Arrays.asList(new Frage_Fragebogen[] { ff, ff2 }));

		updateSkala3.setFragen(Arrays.asList(new Frage[] { i1, i2 }));

		updateSkala4 = new Skala();
		updateSkala4.setId(4);
		updateSkala4.setName("asdf");

		Frage i3 = new Frage();

		updateSkala4.setFragen(Arrays.asList(new Frage[] { i3 }));

		// updateSkala4.setItems(Arrays.asList(new Item[] { new Item() }));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(skalaDAO).persist(Mockito.any(Skala.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(skalaDAO).merge(Mockito.any(Skala.class));

		Mockito.when(skalaDAO.findById(updateSkala1.getId())).thenReturn(updateSkala1);
		Mockito.when(skalaDAO.findById(updateSkala2.getId())).thenReturn(updateSkala2);
		Mockito.when(skalaDAO.findById(updateSkala3.getId())).thenReturn(updateSkala3);
		Mockito.when(skalaDAO.findById(updateSkala4.getId())).thenReturn(updateSkala4);

		setValueToField(skalaDAO, "skalaDAO", model);
	}

}
