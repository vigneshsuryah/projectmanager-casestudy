package com.vigneshsuryah.springboot.bo;

public class ProjectVO {
	private Long projectId;
	private String projectName;
	private String startDate;
	private String endDate;
	private String priority;
	private String status;
	private String managerId;
	private Long noOfTasks;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public Long getNoOfTasks() {
		return noOfTasks;
	}

	public void setNoOfTasks(Long noOfTasks) {
		this.noOfTasks = noOfTasks;
	}
}
