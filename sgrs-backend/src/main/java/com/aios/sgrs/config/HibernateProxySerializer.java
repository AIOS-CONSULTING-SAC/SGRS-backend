package com.aios.sgrs.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;

public class HibernateProxySerializer extends JsonSerializer<HibernateProxy> {

    @Override
    public void serialize(HibernateProxy proxy, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        Object entity = proxy.getHibernateLazyInitializer().getImplementation();
        provider.defaultSerializeValue(entity, generator);
    }

}
