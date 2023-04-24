package com.lru.exceptions;

import com.lru.constants.ErrorCodes;

public class EmptyCacheException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmptyCacheException(final ErrorCodes errorCode, final String message) {
		
		super(new StringBuilder(errorCode.name()).append("-").append(message).toString());
		
	}
	
	public EmptyCacheException(final ErrorCodes errorCode, final String message, final Throwable cause) {
		
		super(new StringBuilder(errorCode.name()).append("-").append(message).toString(), cause);
		
	}

}
