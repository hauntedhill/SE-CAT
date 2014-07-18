package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

/**
 * Staticmetamodel fuer die {@link Item}-Entity
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Item.class)
public class Item_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Item, String> name;

	public static volatile SingularAttribute<Item, String> notiz;

	public static volatile SingularAttribute<Item, String> frage;

	public static volatile SingularAttribute<Item, Bereich> bereich;

	public static volatile ListAttribute<Item, Eigenschaft> eigenschaften;

	public static volatile ListAttribute<Item, Perspektive> perspektiven;

	public static volatile ListAttribute<Item, Bewertung> bewertungen;

	public static volatile ListAttribute<Item, Fragebogen> frageboegen;

	// public static volatile SingularAttribute<Item, Skala> skala;

}
