package com.showflix.app.dao.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"CountryName"})) 
public class Countries {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CountryId")
	private int id;
	@Column(name="CountryName")
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy="countries")
	private Collection<ShowDetails> shows = new ArrayList<ShowDetails>();
	
	public Collection<ShowDetails> getShows() {
		return shows;
	}
	public void setShows(Collection<ShowDetails> shows) {
		this.shows = shows;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int hashCode(){
		return name.hashCode()^Integer.valueOf(id).hashCode();
	}
	public boolean equals(Object o){
		if(o instanceof Countries){
			if(((Countries)o).getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
}
