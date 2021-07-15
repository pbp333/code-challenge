package com.codechallenge.commitviewer.web.config;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.codechallenge.commitviewer.web.ApiObjectMapper;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        var mapper = new ApiObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));

        var apiMessageConverter = new MappingJackson2HttpMessageConverter(mapper);

        converters.add(apiMessageConverter);

    }

}
