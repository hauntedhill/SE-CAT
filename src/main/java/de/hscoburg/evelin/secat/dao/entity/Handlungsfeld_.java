package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Handlungsfeld.class)
public class Handlungsfeld_ {

	public static volatile SingularAttribute<Handlungsfeld, Integer> id;
	public static volatile SingularAttribute<Handlungsfeld, Boolean> aktiv;
	public static volatile SingularAttribute<Handlungsfeld, String> name;
	public static volatile SingularAttribute<Handlungsfeld, String> notiz;
	public static volatile ListAttribute<Handlungsfeld, Item> items;

}
