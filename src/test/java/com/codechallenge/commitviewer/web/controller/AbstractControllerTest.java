package com.codechallenge.commitviewer.web.controller;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import com.codechallenge.commitviewer.web.ApiObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractControllerTest<T> {

    private MockMvc mockMvc;

    @Before
    public void init() {

        var messageConverters = new MappingJackson2HttpMessageConverter();

        var mapper = new ApiObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));

        messageConverters.setObjectMapper(mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(getControllerUnderTest()).setMessageConverters(messageConverters)
                .setLocaleResolver(new FixedLocaleResolver()).build();
    }

    protected abstract T getControllerUnderTest();

    protected MockMvc getMockMvc() {

        return this.mockMvc;
    }

}
