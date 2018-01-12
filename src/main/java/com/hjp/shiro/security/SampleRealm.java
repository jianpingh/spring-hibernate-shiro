package com.hjp.shiro.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjp.shiro.dao.UserDAO;
import com.hjp.shiro.model.Role;
import com.hjp.shiro.model.User;

@Component
public class SampleRealm extends AuthorizingRealm {

	protected UserDAO userDAO = null;

	public SampleRealm() {
		setName("SampleRealm");
		setCredentialsMatcher(new Sha256CredentialsMatcher());
	}

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userDAO.findUser(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Long userId = (Long) principals.fromRealm(getName()).iterator().next();
		User user = userDAO.getUser(userId);
		
		if(user!=null){
			SimpleAuthorizationInfo info= new SimpleAuthorizationInfo();
			for(Role role :user.getRoles()){
				info.addRole(role.getName());
				info.addStringPermissions(role.getPermissions());
			}
			return info;
		} else{
			return null;
		}
	}

}
