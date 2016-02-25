package com.showflix.app.dao.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"userName"}))
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="UserId")
	private Integer id;
	@Column(name ="FirstName")
	private String firstName;
	@Column(name ="LastName")
	private String lastName;
	@Column(name ="UserName")
	private String userName;
	@Column(name ="Password")
	private String password;
	
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "UserID") , 
						   inverseJoinColumns = @JoinColumn(name = "RoleID") )
	@GenericGenerator(name="hilo-gen",strategy="hilo")
	@CollectionId(
		        columns = @Column(name="UserRoleId"), 
		        type=@Type(type="long"), 
		        generator = "hilo-gen"
		    )
	private Collection<Role> roles;
	
	
	public User(){
		roles = new ArrayList<Role>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}



}