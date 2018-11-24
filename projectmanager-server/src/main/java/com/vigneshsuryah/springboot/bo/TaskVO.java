package com.vigneshsuryah.springboot.bo;

public class TaskVO {
	private Long taskId;
	private String taskName;
	private String startDate;
	private String endDate;
	private String priority;
	private String status;
	private ParentTaskVO parentTask;
	private UserVO employeeDetails;
	private ProjectVO projectDetails;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public ParentTaskVO getParentTask() {
		return parentTask;
	}

	public void setParentTask(ParentTaskVO parentTask) {
		this.parentTask = parentTask;
	}

	public UserVO getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(UserVO employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public ProjectVO getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(ProjectVO projectDetails) {
		this.projectDetails = projectDetails;
	}
}
