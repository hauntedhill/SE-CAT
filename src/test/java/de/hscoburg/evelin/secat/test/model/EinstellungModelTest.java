package de.hscoburg.evelin.secat.test.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import de.hscoburg.evelin.secat.dao.EinstellungDAO;
import de.hscoburg.evelin.secat.dao.entity.Einstellung;
import de.hscoburg.evelin.secat.dao.entity.base.EinstellungenType;
import de.hscoburg.evelin.secat.model.EinstellungModel;
import de.hscoburg.evelin.secat.test.mock.BaseModelTest;

public class EinstellungModelTest extends BaseModelTest {

	private static EinstellungModel model = new EinstellungModel();

	@Test
	public void save1() {

		Mockito.when(einstellungenDAO.findByName(EinstellungenType.STANDORT)).thenReturn(null);

		model.saveEinstellung(EinstellungenType.STANDORT, "asdf");

		Assert.assertTrue(persistObjects.size() == 1);
		Assert.assertTrue(((Einstellung) persistObjects.get(0)).getWert().equals("asdf"));
	}

	@Test
	public void merge() {

		Einstellung e = new Einstellung();
		e.setName(EinstellungenType.STANDORT);
		e.setWert("test");

		Mockito.when(einstellungenDAO.findByName(EinstellungenType.STANDORT)).thenReturn(e);

		model.saveEinstellung(EinstellungenType.STANDORT, "asdf");

		Assert.assertTrue(mergedObjects.size() == 1);
		Assert.assertTrue(((Einstellung) mergedObjects.get(0)).getWert().equals("asdf"));
	}

	// @Test(expected = NullPointerException.class)
	// public void saveNPE() {
	// model.savePerspektive(null);
	// }

	private static EinstellungDAO einstellungenDAO;

	@BeforeClass
	public static void setup() throws Exception {
		einstellungenDAO = Mockito.mock(EinstellungDAO.class);
		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				persistObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(einstellungenDAO).persist(Mockito.any(Einstellung.class));

		Mockito.doAnswer(new Answer<Object>() {

			public Object answer(InvocationOnMock invocation) throws Throwable {

				mergedObjects.add(invocation.getArguments()[0]);
				return null;
			}
		}).when(einstellungenDAO).merge(Mockito.any(Einstellung.class));

		setValueToField(einstellungenDAO, "einstellungDAO", model);
	}

}
