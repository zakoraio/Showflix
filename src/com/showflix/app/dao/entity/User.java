package com.showflix.app.dao.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "userName" }) )
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId")
	private Integer id;
	@Column(name = "FirstName", nullable = false)
	private String firstName;
	@Column(name = "LastName")
	private String lastName;
	@Column(name = "UserName", nullable = false)
	private String userName;
	@Column(name = "Password", nullable = false)
	private String password;
	@Column(name = "Email", nullable = false)
	private String email;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "UserID") , inverseJoinColumns = @JoinColumn(name = "RoleID") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "UserRoleId") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Role> roles;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "UserComments", joinColumns = @JoinColumn(name = "UserID") , inverseJoinColumns = @JoinColumn(name = "CommentID") )
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = @Column(name = "UserCommentID") , type = @Type(type = "long") , generator = "hilo-gen")
	private Collection<Comment> comments;

	public User() {
		roles = new ArrayList<Role>();
		comments = new ArrayList<Comment>();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public void fetch(User user) {
		this.email = user.email;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.password = user.password;
	}

}
