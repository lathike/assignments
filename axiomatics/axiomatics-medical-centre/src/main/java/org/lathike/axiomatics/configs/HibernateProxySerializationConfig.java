package org.lathike.axiomatics.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class HibernateProxySerializationConfig {

    @Bean
    public MappingJackson2HttpMessageConverter hibernateAwareObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Hibernate5Module module = new Hibernate5Module();
        module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
        module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModule(module);

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(mapper);
        return jackson2HttpMessageConverter;
    }
}
