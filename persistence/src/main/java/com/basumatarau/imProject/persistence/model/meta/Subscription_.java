package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.ChatRoom;
import com.basumatarau.imProject.persistence.model.Subscription;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subscription.class)
public abstract class Subscription_ extends ContactEntry_ {

	public static volatile SingularAttribute<Subscription, Boolean> isEnabled;
	public static volatile SingularAttribute<Subscription, Subscription.ChatRoomPrivilege> privilege;
	public static volatile SingularAttribute<Subscription, Long> enteredChat;
	public static volatile SingularAttribute<Subscription, ChatRoom> chatRoom;

	public static final String IS_ENABLED = "isEnabled";
	public static final String PRIVILEGE = "privilege";
	public static final String ENTERED_CHAT = "enteredChat";
	public static final String CHAT_ROOM = "chatRoom";

}

