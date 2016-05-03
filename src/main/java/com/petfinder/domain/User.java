package com.petfinder.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="users")
public class User extends AbstractPersistable<Long>{
   
	@Column(name = "login", unique = true, nullable = false)
    private String login;
	
	@Column(name = "email", unique = true, nullable = false)
    private String email;
	
	@Column(name = "password", nullable = false)
    private String password;
	
	@Column(name = "isActivated", nullable = false)
    private boolean isActivated;
	
	@Column(name = "isBanned", nullable = false)
    private boolean isBanned;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false)
    private Date registration_date;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_date", nullable = true)
    private Date last_login_date;
	public User() {
		super();
	}
    public User(String login, String email, String password){
    	Date date= new Date();	

    	this.login = login;
    	this.email=email;
    	this.password=password;
    	this.isBanned = false;
    	this.isActivated = false;
    	this.registration_date = new Timestamp(date.getTime());
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}

	public Date getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(Date last_login_date) {
		this.last_login_date = last_login_date;
	}
}
