package com.codechallenge.commitviewer.web;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -5276654900688714962L;

    public ApiObjectMapper() {

        findAndRegisterModules();
    }

}
