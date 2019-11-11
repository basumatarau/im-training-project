package com.basumatarau.imProject.persistence.model.meta.user;

import com.basumatarau.imProject.persistence.model.user.AuthenticationDetails;
import com.basumatarau.imProject.persistence.model.user.ExternalId;
import com.basumatarau.imProject.persistence.model.user.Identity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Identity.class)
public abstract class Identity_ {

	public static volatile SingularAttribute<Identity, ExternalId> externalId;
	public static volatile SingularAttribute<Identity, AuthenticationDetails> details;
	public static volatile SingularAttribute<Identity, Long> id;

	public static final String EXTERNAL_ID = "externalId";
	public static final String DETAILS = "details";
	public static final String ID = "id";

}

