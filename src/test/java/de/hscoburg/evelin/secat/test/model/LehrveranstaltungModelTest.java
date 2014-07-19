package de.hscoburg.evelin.secat.test.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.LehrveranstaltungDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.dao.entity.base.SemesterType;
import de.hscoburg.evelin.secat.model.LehrveranstaltungModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;
import edu.emory.mathcs.backport.java.util.Arrays;

public class LehrveranstaltungModelTest extends BaseModelTest {

	private static LehrveranstaltungModel model = new LehrveranstaltungModel();

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter1() {
		model.saveLehrveranstaltung("", null, null, null);
	}

	@Test(expected = NullPointerException.class)
	public void saveNPE() {
		model.saveLehrveranstaltung(null, new Fach(), 2014, SemesterType.SS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter6() {
		model.saveLehrveranstaltung("", new Fach(), 2014, SemesterType.SS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter2() {
		model.saveLehrveranstaltung("123", new Fach(), 2014, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter3() {
		model.saveLehrveranstaltung("123", null, 2014, SemesterType.SS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter4() {
		model.saveLehrveranstaltung("123", new Fach(), null, SemesterType.SS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter5() {
		model.saveLehrveranstaltung("123", null, 2014, SemesterType.SS);
	}

	@Test
	public void saveEigenschaft() {
		model.saveLehrveranstaltung("123", new Fach(), 2014, SemesterType.SS);

		Assert.assertTrue(persistObjects.size() == 1);
		Assert.assertTrue(((Lehrveranstaltung) persistObjects.get(0)).getDozent().equals("123"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateWrongParameter() {

		Lehrveranstaltung e = new Lehrveranstaltung();
		e.setDozent("");

		model.updateLehrveranstaltung(e);
	}

	@Test(expected = NullPointerException.class)
	public void updateNPE() {
		model.updateLehrveranstaltung(null);
	}

	@Test
	public void updateNoDependencies() {
		updateLehrveranstaltung1.setDozent("test1");
		updateLehrveranstaltung1.setFach(new Fach());
		updateLehrveranstaltung1.setJahr(new Date());
		updateLehrveranstaltung1.setSemester(SemesterType.SS);
		model.updateLehrveranstaltung(updateLehrveranstaltung1);

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Lehrveranstaltung) mergedObjects.get(0)).getDozent().equals("test1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked1() {
		// updateLehrveranstaltung2.setDozent("test1");
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked2() {
		// updateLehrveranstaltung2.setDozent("test1");
		updateLehrveranstaltung2.setJahr(new Date());
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked3() {
		updateLehrveranstaltung2.setJahr(new Date());
		updateLehrveranstaltung2.setSemester(SemesterType.SS);
		// updateLehrveranstaltung2.setFach(new Fach());
		// updateLehrveranstaltung2.setJahr(new Date());
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked4() {
		updateLehrveranstaltung2.setJahr(new Date());
		updateLehrveranstaltung2.setSemester(SemesterType.SS);
		updateLehrveranstaltung2.setFach(new Fach());
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked7() {
		updateLehrveranstaltung2.setDozent("123");
		updateLehrveranstaltung2.setFach(new Fach());
		updateLehrveranstaltung2.setJahr(new Date());
		updateLehrveranstaltung2.setSemester(SemesterType.SS);
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked5() {
		updateLehrveranstaltung2.setDozent("");
		updateLehrveranstaltung2.setFach(new Fach());
		updateLehrveranstaltung2.setJahr(new Date());
		updateLehrveranstaltung2.setSemester(SemesterType.SS);
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	@Test(expected = NullPointerException.class)
	public void updateLocked6() {
		updateLehrveranstaltung2.setDozent(null);
		updateLehrveranstaltung2.setFach(new Fach());
		updateLehrveranstaltung2.setJahr(new Date());
		updateLehrveranstaltung2.setSemester(SemesterType.SS);
		model.updateLehrveranstaltung(updateLehrveranstaltung2);

	}

	private static LehrveranstaltungDAO perspektivenDAO;

	private static Lehrveranstaltung updateLehrveranstaltung1;

	private static Lehrveranstaltung updateLehrveranstaltung2;

	@BeforeClass
	public static void setup() throws Exception {
		perspektivenDAO = Mockito.mock(LehrveranstaltungDAO.class);

		updateLehrveranstaltung1 = new Lehrveranstaltung();
		updateLehrveranstaltung1.setId(1);
		updateLehrveranstaltung1.setDozent("test");

		Fragebogen f1 = new Fragebogen();
		f1.setExportiertQuestorPro(false);

		Fragebogen f2 = new Fragebogen();
		f2.setExportiertQuestorPro(true);

		updateLehrveranstaltung2 = new Lehrveranstaltung();
		updateLehrveranstaltung2.setId(2);
		updateLehrveranstaltung2.setDozent("asdf");

		updateLehrveranstaltung2.setFrageboegen((Arrays.asList(new Fragebogen[] { f1, f2 })));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(perspektivenDAO).persist(Mockito.any(Lehrveranstaltung.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(perspektivenDAO).merge(Mockito.any(Lehrveranstaltung.class));

		Mockito.when(perspektivenDAO.findById(updateLehrveranstaltung1.getId())).thenReturn(updateLehrveranstaltung1);

		Mockito.when(perspektivenDAO.findById(updateLehrveranstaltung2.getId())).thenReturn(updateLehrveranstaltung2);

		setValueToField(perspektivenDAO, "lehrveranstaltungsDAO", model);
	}

}
