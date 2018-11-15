package com.vigneshsuryah.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	 @Id
	 @Column(name="employee_id")
	 private String employeeId;
	 
	 @Column(name="first_name")
	 private String firstName;
	 
	 @Column(name="last_name")
	 private String lastName;
	 
	 @Column(name="status")
	 private String status;
	
	 public User() {
		 
	 }
	 
	 public User(String employeeId, String firstName, String lastName, String status) {
		 this.employeeId = employeeId;
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.status = status;
	 }
	 
		public String getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
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
		public String getStatus() {
			return status;
		}
		public void setStatu(String status) {
			this.status = status;
		}

}
