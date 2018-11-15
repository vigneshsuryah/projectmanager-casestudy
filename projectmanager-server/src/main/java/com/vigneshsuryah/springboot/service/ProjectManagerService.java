package com.vigneshsuryah.springboot.service;

import java.util.List;

import com.vigneshsuryah.springboot.bo.TaskVO;

public interface ProjectManagerService {
	
	public List<TaskVO> retriveTasks();
	
	/*public void updateTask(Task task);
	
	public Task getTask(Long taskId);*/

}
