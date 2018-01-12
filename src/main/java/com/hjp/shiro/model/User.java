package com.hjp.shiro.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="users")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class User {

	private Long id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private Set<Role> roles = new HashSet<Role>();
	
    @Id
    @GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getUsername() {
		return username;
	}    
	
	@Basic(optional=false)
    @Column(length=100)
    @Index(name="idx_users_username")
	public void setUsername(String username) {
		this.username = username;
	}
	
    @Basic(optional=false)
    @Index(name="idx_users_email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Basic(optional=false)
    @Column(length=255)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
    @ManyToMany
    @JoinTable(name="users_roles")
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
}
