package com.codechallenge.commitviewer.web.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codechallenge.commitviewer.application.exception.ApplicationException;
import com.codechallenge.commitviewer.application.exception.BusinessException;
import com.codechallenge.commitviewer.application.exception.TechnicalException;
import com.codechallenge.commitviewer.web.json.ErrorJson;

public abstract class ErrorController {

    private static final Logger LOGGER = Logger.getGlobal();

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorJson handleGeneric(Exception exception) {

        LOGGER.log(Level.ALL, exception.getMessage(), exception);

        var message = "An unexpected error has ocurred.";

        return ErrorJson.from(message);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ErrorJson handleBusinessError(BusinessException exception) {

        LOGGER.log(Level.ALL, exception.getMessage(), exception);

        return ErrorJson.from(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ErrorJson handleApplicationError(ApplicationException exception) {

        LOGGER.log(Level.ALL, exception.getMessage(), exception);

        return ErrorJson.from(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ErrorJson handleTechnicalError(TechnicalException exception) {

        LOGGER.log(Level.ALL, exception.getMessage(), exception);

        return ErrorJson.from(exception.getMessage());
    }

}
