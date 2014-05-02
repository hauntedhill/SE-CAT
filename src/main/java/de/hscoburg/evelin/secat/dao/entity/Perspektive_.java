package de.hscoburg.evelin.secat.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Perspektive.class)
public class Perspektive_ {
	public static volatile SingularAttribute<Perspektive, Integer> id;
	public static volatile SingularAttribute<Perspektive, Boolean> aktiv;
	public static volatile SingularAttribute<Perspektive, String> name;

	public static volatile ListAttribute<Perspektive, Item> items;

	public static volatile ListAttribute<Perspektive, Bewertender> bewertende;
}
