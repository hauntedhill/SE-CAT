package de.hscoburg.evelin.secat.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.hscoburg.evelin.secat.model.EigenschaftenModel;
import de.hscoburg.evelin.secat.test.mock.DAOLayerMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DAOLayerMock.class })
public class EigenschaftenModelTest_ {

	@Autowired
	private EigenschaftenModel model;

	@Test(expected = IllegalArgumentException.class)
	public void saveWrongParameter() {
		model.saveEigenschaft("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void save() {
		model.saveEigenschaft("234");
	}

}
