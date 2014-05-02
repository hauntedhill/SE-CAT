package de.hscoburg.evelin.secat.dao;

import org.springframework.stereotype.Repository;

import de.hscoburg.evelin.secat.dao.base.BaseDAO;
import de.hscoburg.evelin.secat.dao.entity.Item;

@Repository
public class ItemDAO extends BaseDAO<Item> {

	public ItemDAO() {
		super(Item.class);

	}

}
