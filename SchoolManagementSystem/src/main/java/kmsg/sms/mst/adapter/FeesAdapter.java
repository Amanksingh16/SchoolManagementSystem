package kmsg.sms.mst.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.Constants;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.FeeDaoImpl;
import kmsg.sms.mst.model.ClassFeeHead;
import kmsg.sms.mst.model.ClassFeeStudents;
import kmsg.sms.mst.model.ClassFees;
import kmsg.sms.mst.model.FeeCollections;
import kmsg.sms.mst.model.FeeHead;

@Component
public class FeesAdapter 
{
	@Autowired
	FeeDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getFeeHeads() 
	{
		Map<String,Object> map = dao.selectFeeHeads();
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			List<FeeHead> listFeeHead = (List<FeeHead>) map.get("lstFeeHead");
			List<FeeHead> lstFeeHead = new ArrayList<>();
			
			for(FeeHead i : listFeeHead)
			{
				map = dao.selectFeeHeadCollections(i.getFeeHeadId());
				if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
					return map;
				
				List<FeeCollections> lstCol = (List<FeeCollections>) map.get("feeCollections");
				i.setLstCollection(lstCol);
				lstFeeHead.add(i);
			}
			map.clear();
			map.put(Constants.STATUS,Constants.SUCCESS);
			map.put("lstFeeHead",lstFeeHead);
		}
		return map;
	}

	public Map<String, Object> getClassFee(String wingId) 
	{
		return dao.selectClassFees(Integer.parseInt(wingId));
	}

	public Map<String, Object> getClassFeeHeads(String classId) 
	{
		return dao.selectClassFeeHeads(Integer.parseInt(classId));
	}

	public Map<String, Object> saveFeeHeads(String feeHead) 
	{
		int feeHeadId = 0;
		Map<String,Object> map = new HashMap<>();
		FeeHead model = new FeeHead();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(feeHead, FeeHead.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in reading feehead data");
			return map;
		}
		if(model.getFeeHeadId() == 0)
		{
			map = dao.saveFeeHead(model);
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
			feeHeadId = (int)map.get("feeHeadId");
		}
		else
		{
			feeHeadId = model.getFeeHeadId();
			map = dao.updateFeeHead(model);
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
			
			map = dao.deleteFeeHeadCollection(feeHeadId);
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
		}		
		for(FeeCollections i : model.getLstCollection())
		{
			map = dao.insertFeeHeadCollections(i,feeHeadId);
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
		}
		map.clear();
		map.put("feeHeadId", feeHeadId);
		map.put(SvcStatus.STATUS,SvcStatus.SUCCESS);
		map.put(SvcStatus.MSG,"FeeHead Saved");
		return map;
	}
	
	public Map<String, Object> saveFeeClassHeads(String classFeeHead) 
	{
		Map<String,Object> map = new HashMap<>();
		ClassFees model = new ClassFees();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(classFeeHead, ClassFees.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in reading class feehead data");
			return map;
		}
		int count = dao.checkClassFeeHeads(model.getClassId());
		
		if(count == -1)
		{
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in checking class FeeHeads");
			return map;				
		}

		if(count > 0)
		{
			map = dao.DeleteClassFeeHead(model.getClassId());
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
			
			map = dao.DeleteClassFeeHeadCollections(model.getClassId());
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
		}
			
		for(ClassFeeHead fee : model.getClassFeeHead())
		{
			map = dao.insertClassFeeHead(fee, model.getClassId());
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
			
			for(FeeCollections col : fee.getFeeCollections())
			{
				map = dao.insertClassFeeHeadCollections(col,fee.getFeeHeadId(),model.getClassId());
				if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
					return map;
			}
		}
		map.put(SvcStatus.STATUS,SvcStatus.SUCCESS);
		map.put(SvcStatus.MSG,"Class FeeHeads Saved");
		return map;						
	}
	
	public Map<String, Object> DeleteFeeClassHead(String classId) 
	{
		return dao.DeleteClassFeeHead(Integer.parseInt(classId));
	}
	
	public Map<String, Object> DeleteFeeHead(String feeHeadId) 
	{
		return dao.DeleteFeeHead(Integer.parseInt(feeHeadId));
	}

	public Map<String, Object> SelectClassFeeStudents(String classId) 
	{
		return dao.selectFeeClassStudents(Integer.parseInt(classId));
	}
	
	public Map<String, Object> saveStudentFeeHeads(String studentFeeHead) 
	{
		Map<String,Object> map = new HashMap<>();
		ClassFeeStudents model = new ClassFeeStudents();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(studentFeeHead, ClassFeeStudents.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in reading student feehead data");
			return map;
		}

		map = dao.DeleteStudentFeeHeadCollections(model.getClassId());
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		map = dao.updateStudentFeeHead(model);
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		for(FeeCollections col : model.getFeeCollection())
		{
			map = dao.insertStudentFeeHeadCollections(col,model.getFeeHeadId(),model.getStudentId());
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
				return map;
		}
			
		map.put(SvcStatus.STATUS,SvcStatus.SUCCESS);
		map.put(SvcStatus.MSG,"Student FeeHeads Saved");
		return map;						
	}
}
