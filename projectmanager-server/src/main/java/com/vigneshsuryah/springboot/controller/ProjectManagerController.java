package com.vigneshsuryah.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vigneshsuryah.springboot.bo.TaskVO;
import com.vigneshsuryah.springboot.service.ProjectManagerService;

@RestController
public class ProjectManagerController {
	
	@Autowired
	private ProjectManagerService projectManagerService;
	
	public void setprojectManagerService(ProjectManagerService projectManagerService) {
		this.projectManagerService = projectManagerService;
	}
	
	@GetMapping("/hometest")
	public String testHome() {
		return "Task Manager App - Test String - Vignesh Suryah";
	}
	
	@GetMapping("/api/tasks")
	public List<TaskVO> getTasks() {
		List<TaskVO> tasks = projectManagerService.retriveTasks();
		return tasks;
	}
	
	/*@GetMapping("/api/tasks/{taskId}")
	public Task getTask(@PathVariable(name="taskId") Long taskId) {
		Task task = projectManagerService.getTask(taskId);
		return task;
	}
	
	@PostMapping(path = "/api/tasks", consumes = "application/json", produces = "application/json")
	public boolean saveTask(@RequestBody Task task) {
		try {
			projectManagerService.updateTask(task);
		}catch(Exception e)
		{
			System.out.println("Save Task Failed : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	@PutMapping("/api/tasks/{taskId}")
	public boolean updateTask(@RequestBody Task task, @PathVariable(name="taskId") Long taskId) {
		try {
			Task taskRetrived = projectManagerService.getTask(taskId);
			if(taskRetrived != null) {
				projectManagerService.updateTask(task);
			}else {
				System.out.println("updateTask: No task available in the task id : " + taskId);
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Update Task Failed : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	@DeleteMapping("/api/tasks/{taskId}")
	public boolean deleteTask(@PathVariable(name="taskId") Long taskId) {
		try {
			Task taskRetrived = projectManagerService.getTask(taskId);
			if(taskRetrived != null) {
				taskRetrived.setStatus("I");
				projectManagerService.updateTask(taskRetrived);
			}else {
				System.out.println("deleteTask: No task available in the task id : " + taskId);
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Delete Task Failed : " + e.getMessage());
			return false;
		}
		return true;
	}*/
}
