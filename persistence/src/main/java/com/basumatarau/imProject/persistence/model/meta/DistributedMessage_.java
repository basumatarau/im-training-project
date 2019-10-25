package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.ChatRoom;
import com.basumatarau.imProject.persistence.model.DistributedMessage;
import com.basumatarau.imProject.persistence.model.StatusInfo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DistributedMessage.class)
public abstract class DistributedMessage_ extends Message_ {

	public static volatile SingularAttribute<DistributedMessage, ChatRoom> chatRoom;
	public static volatile SetAttribute<DistributedMessage, StatusInfo> deliveries;

	public static final String CHAT_ROOM = "chatRoom";
	public static final String DELIVERIES = "deliveries";

}

