/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.league.participation.manager.rest;

import cz.muni.fi.pa165.pokemon.league.participation.manager.exceptions.NoSuchEntityException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Jiri Medved
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested entity not found.")
    @ExceptionHandler(NoSuchEntityException.class)
    public void handleNoSuchEntityException() {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found.")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException() {
    }

}
