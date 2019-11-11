package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.Message;
import com.basumatarau.imProject.persistence.model.MessageResource;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageResource.class)
public abstract class MessageResource_ {

	public static volatile SingularAttribute<MessageResource, String> name;
	public static volatile SingularAttribute<MessageResource, Long> id;
	public static volatile SingularAttribute<MessageResource, Message> message;
	public static volatile SingularAttribute<MessageResource, byte[]> binData;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String BIN_DATA = "binData";

}

