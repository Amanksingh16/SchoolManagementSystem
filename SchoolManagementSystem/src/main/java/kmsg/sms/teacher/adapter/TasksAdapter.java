package kmsg.sms.teacher.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.DaoHandler;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.teacher.daoimpl.TasksDaoImpl;
import kmsg.sms.teacher.model.TasksModel;
import kmsg.sms.teacher.model.TeacherTaskModel;

@Component
public class TasksAdapter implements SMSLogger
{
	@Autowired
	TasksDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getTasks() 
	{
		Map<String,Object> map = dao.listTasks();
		List<TasksModel> finalList = new ArrayList<>();
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			List<TasksModel> lstTask = (List<TasksModel>) map.get("lstTasks");
			map.clear();
			for(int i = 0; i < lstTask.size();i++)
			{
				TasksModel model = new TasksModel();
				model = lstTask.get(i);
				
				map = dao.listTaskTeachers(model.getTaskId());
				if(!map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
					return map;
				
				model.setTeacherTasks((List<TeacherTaskModel>) map.get("lstTeacherTasks"));
				
				finalList.add(model);
			}
			map.put(SvcStatus.STATUS,SvcStatus.SUCCESS);
			map.put("lstTasks",finalList);
			return map;
		}
		return map;
	}

	public Map<String, Object> saveTask(String task) 
	{
		Map<String,Object> map = new HashMap<>();
		TasksModel model = new TasksModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(task, TasksModel.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception Occured in saving task");
		}
		DaoHandler dh = new DaoHandler();
		dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
		
		if(model.getTaskId() == 0)
		{
			map = dao.insertNewTask(model);
			
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
			{
				for(int i = 0; i < model.getTeacherTasks().size();i++)
				{
					model.getTeacherTasks().get(i).setTaskId((int)map.get("taskId"));
					if(dao.insertTeacherTask(model.getTeacherTasks().get(i))==0)
					{
						dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
						logger.error("Error occured in saving teachers for the task");
						return SvcStatus.GET_FAILURE("Error occured in saving teachers for the task");
					}
				}
				dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
				map.put(SvcStatus.MSG,"New Task Added");
				return map;
			}
		}
		else
		{
			map = dao.updateTask(model);
			
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
			{
				map.clear();
				try {
					if(dao.deleteTeacherTasks(model.getTaskId())==0)
					{
						dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
						logger.error("Error occured in deleting teachers for the task");
						return SvcStatus.GET_FAILURE("Error occured in deleting teachers for the task");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
					logger.error("Exception occured in deleting teachers for the task "+e);
					return SvcStatus.GET_FAILURE("Exception occured in deleting teachers for the task");
				}
					
				for(int i = 0; i < model.getTeacherTasks().size();i++)
				{
					model.getTeacherTasks().get(i).setTaskId(model.getTaskId());
					try {
						if(dao.insertTeacherTask(model.getTeacherTasks().get(i))==0)
						{
							dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
							logger.error("Error occured in saving teachers for the task");
							return SvcStatus.GET_FAILURE("Error occured in saving teachers for the task");
						}
					}
					catch(DuplicateKeyException e)
					{
						continue;
					}
					catch(Exception e)
					{
						e.printStackTrace();
						dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
						logger.error("Exception occured in saving teachers for the task "+e);
						return SvcStatus.GET_FAILURE("Exception occured in saving teachers for the task");
					}
				}
				dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
				return SvcStatus.GET_SUCCESS("Task Updated");
			}
		}
		dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
		return map;
	}
}
