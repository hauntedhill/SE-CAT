package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

@StaticMetamodel(Item.class)
public class Item_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Item, String> name;

	public static volatile SingularAttribute<Item, String> notiz;

	public static volatile SingularAttribute<Item, Handlungsfeld> handlungsfeld;

	public static volatile ListAttribute<Item, Eigenschaft> eigenschaften;

	public static volatile ListAttribute<Item, Perspektive> perspektiven;

	public static volatile ListAttribute<Item, Bewertung> bewertungen;

	public static volatile ListAttribute<Item, Fragebogen> frageboegen;

	public static volatile SingularAttribute<Item, Skala> skala;

}
