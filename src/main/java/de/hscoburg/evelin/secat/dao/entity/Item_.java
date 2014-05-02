package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Item.class)
public class Item_ {

	public static volatile SingularAttribute<Item, Integer> id;
	public static volatile SingularAttribute<Item, Boolean> aktiv;
	public static volatile SingularAttribute<Item, String> name;

	public static volatile SingularAttribute<Item, String> notiz;

	public static volatile SingularAttribute<Item, Handlungsfeld> handlungsfeld;

	public static volatile ListAttribute<Item, Eigenschaft> eigenschaften;

	public static volatile ListAttribute<Item, Perspektive> perspektiven;

	public static volatile ListAttribute<Item, Bewertung> bewertungen;

	public static volatile ListAttribute<Item, Fragebogen> frageboegen;

	public static volatile SingularAttribute<Item, Skala> skala;

}
