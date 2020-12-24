package com.lunatech.security.blocking;

public enum Violations {

	LOGIN("login", 6L), REGISTER("register", 5L), AUTHORIZATION("authorization", 5L), MANAGMENT("managment", 4L)
	,NOT_ALLOWED("not_allowed", 6L), FULL("full", 10L), MAX_REACHED("max_reached", 4L);

	private Violations(String operation, Long maxViolations) {
		this.operation = operation;
		this.maxViolations = maxViolations;
	}

	public String operation;
	public Long maxViolations;
	
	/*
	public static Long getMaxViolations(String operation) {
		
		for ( Violations violation : Violations.values()) {
			if ( violation.operation.equals(operation)) {
				return violation.maxViolations;
			}
		}
		
		return Long.MAX_VALUE;
	}
	*/

}
