package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.hscoburg.evelin.secat.dao.entity.base.StammdatenEntity_;

@StaticMetamodel(Perspektive.class)
public class Perspektive_ extends StammdatenEntity_ {

	public static volatile SingularAttribute<Perspektive, String> name;

	public static volatile ListAttribute<Perspektive, Item> items;

	public static volatile ListAttribute<Perspektive, Bewertender> bewertende;
}
