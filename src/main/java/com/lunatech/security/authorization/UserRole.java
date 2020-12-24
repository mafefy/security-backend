package com.lunatech.security.authorization;

public enum UserRole {

	ROOT("root"), USER("user"), ADMIN("admin");

	private UserRole(String role) {
		this.role = role;
	}

	public String role;

	public boolean hasRole(String role) {
		return this.role.equals(role);
	}
	
	public boolean equals(UserRole userRole) {
		return this.role.equals(userRole.role);
	}
}
