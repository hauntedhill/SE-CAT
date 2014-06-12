package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

/**
 * Staticmetamodel fuer die {@link Eigenschaft}-Entitie
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(Eigenschaft.class)
public class Eigenschaft_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Eigenschaft, String> name;
	public static volatile ListAttribute<Eigenschaft, Item> items;

	public static volatile ListAttribute<Eigenschaft, Fragebogen> frageboegen;
}
