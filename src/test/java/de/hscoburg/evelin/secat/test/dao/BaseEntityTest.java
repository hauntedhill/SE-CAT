package de.hscoburg.evelin.secat.test.dao;

import org.junit.Assert;
import org.junit.Test;

import de.hscoburg.evelin.secat.dao.entity.Item;
import de.hscoburg.evelin.secat.dao.entity.base.BaseEntity;
import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity;

public class BaseEntityTest {

	@Test
	public void equalsTest() {
		Item i = new Item();
		i.setId(1);
		Item i2 = new Item();
		i2.setId(1);

		Assert.assertTrue(i.equals(i2));
		Assert.assertTrue(i.equals((BaseEntity) i2));
		Assert.assertTrue(i.equals((StammdatenEntity) i2));
		Assert.assertFalse(i.equals(null));

		i2.setId(2);
		Assert.assertFalse(i.equals(i2));
		i2.setId(null);
		Assert.assertFalse(i.equals(i2));

		Assert.assertFalse(i.equals(new Object()));

	}

}
