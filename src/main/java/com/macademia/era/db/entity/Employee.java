package com.macademia.era.db.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.macademia.era.util.AppConstant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Entity
@Table(name = "era_employee")
@Getter
@Setter
@NoArgsConstructor

public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "employee_no")
	private String employeeNo;

	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;

	@Column(name = "salary")
	private BigDecimal salary;


	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstant.DATE_FORMAT)
	@Column(name = "birth_date")
	private Date birthDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConstant.DATE_FORMAT)
	@Column(name = "start_date")
	private Date startDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Department department;

}
