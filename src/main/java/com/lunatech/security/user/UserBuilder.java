package com.lunatech.security.user;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.lunatech.security.authorization.UserRole;
import com.lunatech.security.user.dao.UserEntity;
import com.lunatech.security.user.model.Credentials;
import com.lunatech.security.user.model.UserRegisterRequest;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserBuilder {

	public UserBuilder() {
		// TODO Auto-generated constructor stub
	}

	public UserEntity buildUser(Credentials credentials,boolean activated, UserRole userRole ) {
		UserEntity newUser = new UserEntity();
		newUser.setName(credentials.getName());
		newUser.setHash(getHash(credentials));
		// must be activated by admin
		newUser.setActivated(activated);
		newUser.setSuspended(false);
		newUser.setRole(userRole.role);
		newUser.updateAuditing();
		return newUser;
	}
	
		
	public UserEntity buildRoot(String rootName , String rootPassword) {
		UserEntity newUser = new UserEntity();
		newUser.setName(rootName);
		newUser.setHash(getHash(rootName, rootPassword));
		newUser.setRole(UserRole.ROOT.role);
		newUser.setActivated(true);
		newUser.setSuspended(false);
		newUser.updateAuditing();
		return newUser;
	}

	public  String getHash(Credentials creds) {
		return getHash(creds.getName() , creds.getPassword() );
	}
	public  String getHash(String name, String password   ) {
		return Hashing.sha256().hashString(name + password, StandardCharsets.UTF_8).toString();
	}

}
