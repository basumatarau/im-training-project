package com.basumatarau.imProject.persistence.model.meta;

import com.basumatarau.imProject.persistence.model.ImageResource;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ImageResource.class)
public abstract class ImageResource_ extends MessageResource_ {

	public static volatile SingularAttribute<ImageResource, Integer> width;
	public static volatile SingularAttribute<ImageResource, Integer> height;

	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";

}

