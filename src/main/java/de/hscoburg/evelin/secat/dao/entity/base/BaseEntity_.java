package de.hscoburg.evelin.secat.dao.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel fuer die {@link BaseEntity}
 * 
 * @author zuch1000
 * 
 */
@StaticMetamodel(BaseEntity.class)
public class BaseEntity_ {
	public static volatile SingularAttribute<BaseEntity, Integer> id;
}
