package com.macademia.era.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Entity
@Table(name = "era_department")
@Getter
@Setter
@NoArgsConstructor

public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "code")
	private String code;
	
	
	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "department")
	@Column(nullable = true)
	private List<Employee> employees = new ArrayList<Employee>();
	
}
