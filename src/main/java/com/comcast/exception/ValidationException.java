package com.comcast.exception;


/**
 * This class is used to denote the validation failures
 * 
 * @author Anitha Krishnasamy
 *
 */

public class ValidationException extends Exception  {
	
	private static final long serialVersionUID = 1L;

	public ValidationException(String message)
	        {
	            super(message);
	        }

 
}
