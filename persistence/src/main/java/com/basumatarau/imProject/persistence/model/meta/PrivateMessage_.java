package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.PersonalContact;
import com.basumatarau.imProject.persistence.model.PrivateMessage;
import com.basumatarau.imProject.persistence.model.StatusInfo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PrivateMessage.class)
public abstract class PrivateMessage_ extends Message_ {

	public static volatile SingularAttribute<PrivateMessage, StatusInfo> delivery;
	public static volatile SingularAttribute<PrivateMessage, PersonalContact> personalContact;

	public static final String DELIVERY = "delivery";
	public static final String PERSONAL_CONTACT = "personalContact";

}

