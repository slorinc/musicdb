package com.cabonline.musicdb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * PropertySourcesConfiguration
 *
 * @author <a href="mailto:lorinc.sonnevend@betvictor.com">Lorinc Sonnevend</a>
 *         9/25/2015
 */
@Configuration
@PropertySources(value = {
        @PropertySource("classpath:test.coverart.response.json.xml"),
        @PropertySource("classpath:test.musicbrainz.response.json.xml"),
        @PropertySource("classpath:test.wikipedia.api.response.json.xml"),
        @PropertySource("classpath:test-application.properties") })
public class PropertySourcesConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
