package com.vigneshsuryah.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vigneshsuryah.springboot.bo.ParentTaskVO;
import com.vigneshsuryah.springboot.bo.ProjectVO;
import com.vigneshsuryah.springboot.bo.TaskVO;
import com.vigneshsuryah.springboot.bo.UserVO;
import com.vigneshsuryah.springboot.service.ProjectManagerService;

@RestController
public class ProjectManagerController {
	
	@Autowired
	private ProjectManagerService projectManagerService;
	
	public void setprojectManagerService(ProjectManagerService projectManagerService) {
		this.projectManagerService = projectManagerService;
	}
	
	@GetMapping("/test")
	public String test() {
		return "Project Manager App - Creator: Mani, Vignesh Suryah";
	}
	
	@GetMapping("/api/tasks")
	public List<TaskVO> getTasks() {
		List<TaskVO> tasks = projectManagerService.retriveTasks();
		return tasks;
	}
	
	@GetMapping("/api/parenttasks")
	public List<ParentTaskVO> getAllParentTasks() {
		List<ParentTaskVO> tasks = projectManagerService.retriveParentTasks();
		return tasks;
	}
	
	@GetMapping("/api/parenttasks/projects/{projectId}")
	public List<ParentTaskVO> getParentTasksForProjectId(@PathVariable(name="projectId") String projectId) {
		List<ParentTaskVO> tasks = projectManagerService.retriveParentTasksForProjectId(projectId); 
		return tasks;
	}
	
	@PostMapping(path = "/api/parenttasks", consumes = "application/json", produces = "application/json")
	public boolean updateParentTask(@RequestBody ParentTaskVO parentTask) {
		try {
			projectManagerService.updateParentTask(parentTask);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	@GetMapping("/api/projects")
	public List<ProjectVO> getProjects() {
		List<ProjectVO> projects = projectManagerService.retriveProjects();
		return projects;
	}
	
	@PostMapping(path = "/api/projects", consumes = "application/json", produces = "application/json")
	public boolean updateProject(@RequestBody ProjectVO project) {
		try {
			projectManagerService.updateProject(project);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	@GetMapping("/api/users")
	public List<UserVO> getUsers() {
		List<UserVO> users = projectManagerService.retriveUsers();
		return users;
	}
	
	@PostMapping(path = "/api/users", consumes = "application/json", produces = "application/json")
	public boolean updateUsers(@RequestBody UserVO user) {
		try {
			projectManagerService.updateUser(user);
		}catch(Exception e)
		{
			return false;
		}
		return true;
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
