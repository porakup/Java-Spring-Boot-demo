package dev.demo.registration.exception;

@SuppressWarnings("serial")
public class BusinessException extends Exception {
	
	 public BusinessException(String errorMessage) {
	        super(errorMessage);
	    }

}
