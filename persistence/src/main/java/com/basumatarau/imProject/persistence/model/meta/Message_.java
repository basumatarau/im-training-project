package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.Message;
import com.basumatarau.imProject.persistence.model.MessageResource;
import com.basumatarau.imProject.persistence.model.user.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, User> author;
	public static volatile SingularAttribute<Message, Long> timeSent;
	public static volatile SetAttribute<Message, MessageResource> resources;
	public static volatile SingularAttribute<Message, Long> id;
	public static volatile SingularAttribute<Message, String> body;

	public static final String AUTHOR = "author";
	public static final String TIME_SENT = "timeSent";
	public static final String RESOURCES = "resources";
	public static final String ID = "id";
	public static final String BODY = "body";

}

