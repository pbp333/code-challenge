package com.codechallenge.commitviewer.web.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codechallenge.commitviewer.application.core.exception.BusinessException;
import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.web.json.ErrorJson;

public abstract class ErrorController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ErrorController.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorJson handleGeneric(Exception exception) {

        LOGGER.error(exception.getMessage(), exception);

        var message = "An unexpected error has ocurred.";

        return ErrorJson.from(message);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ErrorJson handleBusinessError(BusinessException exception) {

        LOGGER.error(exception.getMessage(), exception);

        return ErrorJson.from(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ErrorJson handleApplicationError(ApplicationException exception) {

        LOGGER.error(exception.getMessage(), exception);

        return ErrorJson.from(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ErrorJson handleTechnicalError(TechnicalException exception) {

        LOGGER.error(exception.getMessage(), exception);

        return ErrorJson.from(exception.getMessage());
    }

}
