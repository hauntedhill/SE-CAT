package de.hscoburg.evelin.secat.test.mock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

public class BaseModelTest {

	protected static List<Object> mergedObjects;

	protected static List<Object> persistObjects;

	static {
		mergedObjects = new ArrayList<Object>();
		persistObjects = new ArrayList<Object>();

	}

	@Before
	public void init() {
		mergedObjects.clear();
		persistObjects.clear();
	}

	public static void setValueToField(Object val, String field, Object target) throws Exception {
		Field f = null;
		try {
			f = target.getClass().getDeclaredField(field);
		} catch (NoSuchFieldException e) {
			f = target.getClass().getSuperclass().getDeclaredField(field);
		}
		f.setAccessible(true);

		f.set(target, val);

	}

}
