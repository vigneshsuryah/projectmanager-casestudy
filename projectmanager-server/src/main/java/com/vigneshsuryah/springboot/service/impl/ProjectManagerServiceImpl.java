package com.vigneshsuryah.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigneshsuryah.springboot.bo.TaskVO;
import com.vigneshsuryah.springboot.entity.Task;
import com.vigneshsuryah.springboot.repository.ProjectManagerRepository;
import com.vigneshsuryah.springboot.repository.TaskManagerRepository;
import com.vigneshsuryah.springboot.service.ProjectManagerService;

@Service
public class ProjectManagerServiceImpl implements ProjectManagerService{

	@Autowired
	private ProjectManagerRepository projectManagerRepository;
	
	@Autowired
	private TaskManagerRepository taskManagerRepository;
	
	@Autowired
	private Mapper dozerMapper;
	
	public void setProjectManagerRepository(ProjectManagerRepository projectManagerRepository) {
		this.projectManagerRepository = projectManagerRepository;
	}
	
	public void setTaskManagerRepository(TaskManagerRepository taskManagerRepository) {
		this.taskManagerRepository = taskManagerRepository;
	}
	
	public List<TaskVO> retriveTasks(){
		List<TaskVO> taskToBeReturned = new ArrayList<TaskVO>();
		List<Task> tasksRetrived = taskManagerRepository.findAll();
		for(Task task: tasksRetrived) {
			taskToBeReturned.add(dozerMapper.map(task, TaskVO.class));
		}
		return taskToBeReturned;
	}

	/*public void updateTask(Task task) {
		projectManagerRepository.save(task);
	}
	
	public Task getTask(Long taskId) {
		Optional<Task> optTask = projectManagerRepository.findById(taskId);
		return optTask.get();
	}*/
}
