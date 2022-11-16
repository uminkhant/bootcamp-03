package com.jdc.mkt;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.CollectionTable;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Column;

@Entity
@Table(name = "person_tbl")
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String phone;
	@ElementCollection
	@CollectionTable(name = "p_school_tbl", joinColumns = @JoinColumn(name = "p_id"))
	private List<School>schools;
	
	public Person() {
		super();
	}
	public Person(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<School> getSchools() {
		return schools;
	}
	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
	
	
}
