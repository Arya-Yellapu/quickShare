package com.test.quickshare.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Users [email=" + email + ", password=" + password + "]";
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

}
