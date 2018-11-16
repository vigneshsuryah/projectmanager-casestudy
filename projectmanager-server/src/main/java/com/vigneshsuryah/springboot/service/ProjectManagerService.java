package com.vigneshsuryah.springboot.service;

import java.util.List;

import com.vigneshsuryah.springboot.bo.ParentTaskVO;
import com.vigneshsuryah.springboot.bo.ProjectVO;
import com.vigneshsuryah.springboot.bo.TaskVO;
import com.vigneshsuryah.springboot.bo.UserVO;

public interface ProjectManagerService {
	
	public List<TaskVO> retriveTasks();
	
	public List<ParentTaskVO> retriveParentTasks();
	public List<ParentTaskVO> retriveParentTasksForProjectId(String projectId);
	public void updateParentTask(ParentTaskVO parentTask);
	
	public List<ProjectVO> retriveProjects();
	public void updateProject(ProjectVO project);
	
	public List<UserVO> retriveUsers();
	public void updateUser(UserVO user);
	
	/*public void updateTask(Task task);
	
	public Task getTask(Long taskId);*/

}
