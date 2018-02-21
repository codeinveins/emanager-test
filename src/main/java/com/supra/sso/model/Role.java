package com.supra.sso.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2691172201754915414L;
	private Long id;
    private String authority;
    private Set<User> users;

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @Column(name="name")
    public String getAuthority() {
    	return authority;
    }
    
    public void setAuthority(String authority) {
    	this.authority = authority;
    }


    @ManyToMany(mappedBy = "authorities")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }



	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + authority + ", users=" + users + "]";
	}

    
    
}