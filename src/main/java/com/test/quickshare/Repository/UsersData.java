package com.test.quickshare.Repository;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users_data")
public class UsersData {

	@Id
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "role")
	private String role;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mail")
	private Users ud;

	@Column(name = "picture_path")
	private String picturePath;

	@Override
	public String toString() {
		return "UsersData [firstName=" + firstName + ", lastName=" + lastName + ", role=" + role + ", ud=" + ud
				+ ", picturePath=" + picturePath + "]";
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Users getUd() {
		return ud;
	}

	public void setUd(Users ud) {
		this.ud = ud;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
