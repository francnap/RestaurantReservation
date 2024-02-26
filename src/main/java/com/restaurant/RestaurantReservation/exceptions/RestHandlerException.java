package com.restaurant.RestaurantReservation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestHandlerException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> missingData(MissingData error){
		ErrorResponse errore = new ErrorResponse();
		errore.setCodice(HttpStatus.NOT_ACCEPTABLE.value());
		errore.setMessaggio(error.getMessaggio());
		return new ResponseEntity<ErrorResponse>(errore, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> notFoundException(NotFoundException error){
		ErrorResponse errore = new ErrorResponse();
		errore.setCodice(HttpStatus.NOT_FOUND.value());
		errore.setMessaggio(error.getMessaggio());
		return new ResponseEntity<ErrorResponse>(errore, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> datiDuplicatiException(DatiDuplicatiException error){
		ErrorResponse errore = new ErrorResponse();
		errore.setCodice(HttpStatus.BAD_REQUEST.value());
		errore.setMessaggio(error.getMessaggio());
		return new ResponseEntity<ErrorResponse>(errore, HttpStatus.BAD_REQUEST);
	}
}