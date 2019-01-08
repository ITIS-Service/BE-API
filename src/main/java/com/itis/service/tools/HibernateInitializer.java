package com.itis.service.tools;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateInitializer {

    @SuppressWarnings("unchecked")
    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);

        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }

        return entity;
    }

    public static <T> List<T> initializeAndUnproxe(List<T> entities) {
        return entities.stream().map(HibernateInitializer::initializeAndUnproxy).collect(Collectors.toList());
    }

}
