package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Eigenschaft.class)
public class Eigenschaft_ {
	public static volatile SingularAttribute<Eigenschaft, Integer> id;
	public static volatile SingularAttribute<Eigenschaft, Boolean> aktiv;
	public static volatile SingularAttribute<Eigenschaft, String> name;
	public static volatile ListAttribute<Eigenschaft, Item> items;
}
