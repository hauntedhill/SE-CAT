package de.hscoburg.evelin.secat.test.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.FachDAO;
import de.hscoburg.evelin.secat.dao.entity.Fach;
import de.hscoburg.evelin.secat.dao.entity.Fragebogen;
import de.hscoburg.evelin.secat.dao.entity.Lehrveranstaltung;
import de.hscoburg.evelin.secat.model.FachModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;
import edu.emory.mathcs.backport.java.util.Arrays;

public class FachModelTest extends BaseModelTest {

	private static FachModel model = new FachModel();

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter() {
		model.saveFach("");
	}

	@Test(expected = NullPointerException.class)
	public void saveNPE() {
		model.saveFach(null);
	}

	@Test
	public void saveEigenschaft() {
		model.saveFach("test");

		Assert.assertTrue(persistObjects.size() == 1);
		Assert.assertTrue(((Fach) persistObjects.get(0)).getName().equals("test"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateWrongParameter() {

		Fach e = new Fach();
		e.setName("");

		model.updateFach(e);
	}

	@Test(expected = NullPointerException.class)
	public void updateNPE() {
		model.updateFach(null);
	}

	@Test
	public void updateNoDependencies() {
		updateFach1.setName("test1");
		model.updateFach(updateFach1);

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Fach) mergedObjects.get(0)).getName().equals("test1"));
	}

	@Test
	public void updateLocked1() {
		updateFach2.setName("test1");
		model.updateFach(updateFach2);

	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLocked2() {
		updateFach3.setName("test1");
		model.updateFach(updateFach3);

	}

	private static FachDAO fachDAO;

	private static Fach updateFach1;

	private static Fach updateFach2;

	private static Fach updateFach3;

	@BeforeClass
	public static void setup() throws Exception {
		fachDAO = Mockito.mock(FachDAO.class);

		updateFach1 = new Fach();
		updateFach1.setId(1);
		updateFach1.setName("test");

		Fragebogen f1 = new Fragebogen();
		f1.setExportiertQuestorPro(false);

		Fragebogen f2 = new Fragebogen();
		f2.setExportiertQuestorPro(true);

		updateFach2 = new Fach();
		updateFach2.setId(2);
		updateFach2.setName("asdf");

		updateFach2.setLehrveranstaltungen(Arrays.asList(new Lehrveranstaltung[] { new Lehrveranstaltung() }));

		updateFach3 = new Fach();
		updateFach3.setId(3);
		updateFach3.setName("asdf");

		Lehrveranstaltung l1 = new Lehrveranstaltung();
		l1.setFrageboegen(java.util.Arrays.asList(new Fragebogen[] { f1, f2 }));

		updateFach3.setLehrveranstaltungen(Arrays.asList(new Lehrveranstaltung[] { l1 }));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(fachDAO).persist(Mockito.any(Fach.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(fachDAO).merge(Mockito.any(Fach.class));

		Mockito.when(fachDAO.findById(updateFach1.getId())).thenReturn(updateFach1);

		Mockito.when(fachDAO.findById(updateFach2.getId())).thenReturn(updateFach2);

		Mockito.when(fachDAO.findById(updateFach3.getId())).thenReturn(updateFach3);

		setValueToField(fachDAO, "fachDAO", model);
	}

}
