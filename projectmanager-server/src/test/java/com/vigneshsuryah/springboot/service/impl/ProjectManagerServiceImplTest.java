package com.vigneshsuryah.springboot.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigneshsuryah.springboot.bo.ParentTaskVO;
import com.vigneshsuryah.springboot.bo.ProjectVO;
import com.vigneshsuryah.springboot.bo.TaskVO;
import com.vigneshsuryah.springboot.bo.UserVO;
import com.vigneshsuryah.springboot.entity.ParentTask;
import com.vigneshsuryah.springboot.entity.Project;
import com.vigneshsuryah.springboot.entity.Task;
import com.vigneshsuryah.springboot.entity.User;
import com.vigneshsuryah.springboot.repository.ParentTaskManagerRepository;
import com.vigneshsuryah.springboot.repository.ProjectManagerRepository;
import com.vigneshsuryah.springboot.repository.TaskManagerRepository;
import com.vigneshsuryah.springboot.repository.UserManagerRepository;
import com.vigneshsuryah.springboot.service.impl.ProjectManagerServiceImpl;

@RunWith(SpringRunner.class)
public class ProjectManagerServiceImplTest {
	
	private ProjectManagerRepository projectManagerRepository;
	private TaskManagerRepository taskManagerRepository;
	private ParentTaskManagerRepository parentTaskManagerRepository;
	private UserManagerRepository userManagerRepository;
	
	private ProjectManagerServiceImpl projectManagerServiceImpl;
	@Spy
    private DozerBeanMapper dozerMapper;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() throws Exception {
		projectManagerRepository = Mockito.mock(ProjectManagerRepository.class);
		taskManagerRepository = Mockito.mock(TaskManagerRepository.class);
		parentTaskManagerRepository = Mockito.mock(ParentTaskManagerRepository.class);
		userManagerRepository = Mockito.mock(UserManagerRepository.class);
		projectManagerServiceImpl = new ProjectManagerServiceImpl(projectManagerRepository, taskManagerRepository, parentTaskManagerRepository, userManagerRepository, dozerMapper);
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testRetriveTasks() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<List<Task>> mapType = new TypeReference<List<Task>>() {};
		List<Task> allTasks = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("repo-tasks.json").getFile());
		allTasks = mapper.readValue(file, mapType);
		when(taskManagerRepository.findAll()).thenReturn(allTasks);
		List<TaskVO> taskDetails = projectManagerServiceImpl.retriveTasks();
		Assert.assertNotNull(taskDetails);
		verify(taskManagerRepository,times(1)).findAll();
		verifyNoMoreInteractions(taskManagerRepository);
	}
	
	
	@Test
	public void testUpdateTask() throws JsonParseException, JsonMappingException, IOException {
		Task task = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("repo-task.json").getFile());
		task = mapper.readValue(file, Task.class);
		
		TaskVO taskVO = null;
		File fileTaskVO = new File(classLoader.getResource("task.json").getFile());
		taskVO = mapper.readValue(fileTaskVO, TaskVO.class);
		when(taskManagerRepository.save(task)).thenReturn(task);
		projectManagerServiceImpl.updateTask(taskVO);
	}
	
	@Test
	public void testRetriveParentTasks() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<List<ParentTask>> mapType = new TypeReference<List<ParentTask>>() {};
		List<ParentTask> allParentTasks = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("parenttasks.json").getFile());
		allParentTasks = mapper.readValue(file, mapType);
		when(parentTaskManagerRepository.findAll()).thenReturn(allParentTasks);
		List<ParentTaskVO> allParentTasksDetails = projectManagerServiceImpl.retriveParentTasks();
		Assert.assertNotNull(allParentTasksDetails);
		verify(parentTaskManagerRepository,times(1)).findAll();
		verifyNoMoreInteractions(parentTaskManagerRepository);
	}
	
	@Test
	public void testUpdateParentTask() throws JsonParseException, JsonMappingException, IOException {
		ParentTask parent = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("parenttask.json").getFile());
		parent = mapper.readValue(file, ParentTask.class);
		
		ParentTaskVO parentTaskVO = null;
		File fileTaskVO = new File(classLoader.getResource("parenttask.json").getFile());
		parentTaskVO = mapper.readValue(fileTaskVO, ParentTaskVO.class);
		when(parentTaskManagerRepository.save(parent)).thenReturn(parent);
		projectManagerServiceImpl.updateParentTask(parentTaskVO);
	}
	
	@Test
	public void testRetriveProjects() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<List<Project>> mapType = new TypeReference<List<Project>>() {};
		List<Project> allProjects = null;
		Long projectId = (long) 3;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("repo-projects.json").getFile());
		allProjects = mapper.readValue(file, mapType);
		when(projectManagerRepository.findAll()).thenReturn(allProjects);
		when(taskManagerRepository.getTotalTasksForProjectId(projectId)).thenReturn(projectId);
		List<ProjectVO> allProjectDetails = projectManagerServiceImpl.retriveProjects();
		Assert.assertNotNull(allProjectDetails);
		verify(projectManagerRepository,times(1)).findAll();
		verifyNoMoreInteractions(projectManagerRepository);
	}
	
	@Test
	public void testUpdateProject() throws JsonParseException, JsonMappingException, IOException {
		Project project = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("project.json").getFile());
		project = mapper.readValue(file, Project.class);
		
		ProjectVO projectVO = null;
		File fileProjectVO = new File(classLoader.getResource("project.json").getFile());
		projectVO = mapper.readValue(fileProjectVO, ProjectVO.class);
		when(projectManagerRepository.save(project)).thenReturn(project);
		projectManagerServiceImpl.updateProject(projectVO);
	}
	
	@Test
	public void testRetriveUsers() throws JsonParseException, JsonMappingException, IOException {
		TypeReference<List<User>> mapType = new TypeReference<List<User>>() {};
		List<User> allUsers = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("users.json").getFile());
		allUsers = mapper.readValue(file, mapType);
		when(userManagerRepository.findAll()).thenReturn(allUsers);
		List<UserVO> allUsersDetails = projectManagerServiceImpl.retriveUsers();
		Assert.assertNotNull(allUsersDetails);
		verify(userManagerRepository,times(1)).findAll();
		verifyNoMoreInteractions(userManagerRepository);
	}
	
	@Test
	public void testUpdateUser() throws JsonParseException, JsonMappingException, IOException {
		User user = null;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("user.json").getFile());
		user = mapper.readValue(file, User.class);
		
		UserVO userVO = null;
		File fileUserVO = new File(classLoader.getResource("user.json").getFile());
		userVO = mapper.readValue(fileUserVO, UserVO.class);
		when(userManagerRepository.save(user)).thenReturn(user);
		projectManagerServiceImpl.updateUser(userVO);
	}

}
