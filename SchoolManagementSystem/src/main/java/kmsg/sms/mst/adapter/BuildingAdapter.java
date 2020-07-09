package kmsg.sms.mst.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.BuildingDaoImpl;
import kmsg.sms.mst.model.Building;
import kmsg.sms.mst.model.BuildingRooms;

@Component
public class BuildingAdapter implements SMSLogger
{
	@Autowired
	BuildingDaoImpl dao;
	
	@Value("${saveFilePath}")
	private String saveFilePath;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getBuildingList() 
	{	
		return dao.getBuildingList();
	}

	public Map<String, Object> addBuilding(String building, MultipartFile file) throws IOException 
	{
		Building model = new Building();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(building, Building.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occured in saving building");
		}
		if(file!=null)
		{
			String contractFilePath = saveFilePath+"/"+file.getOriginalFilename();
			File directory = new File(contractFilePath);
			directory.getParentFile().mkdirs();
		      if (!directory.exists())		
					directory.createNewFile();			
			 
			try (FileOutputStream fos = new FileOutputStream(directory)) {
				byte[] imageByte = file.getBytes();
				fos.write(imageByte);
				model.setBuildingPath(contractFilePath);
			} catch (Exception e) {
				e.printStackTrace();
				return SvcStatus.GET_FAILURE("Error saving file for building " + model.getBuildingName() + " : " + e);
			}
		}
		if(model.getBuildingId() == 0)
			return dao.saveBuilding(model);
		else
			return dao.updateBuilding(model);
	}

	public Map<String, Object> getBuildingRoomsList(int buildingId) {
		return dao.getBuildingRoomList(buildingId);
	}

	public Map<String, Object> getBuildingImg(int buildingId) 
	{
		Map<String,Object> data = new HashMap<>();
		String fileName = dao.selectBuildingImgPath(buildingId);
		
		String imageUrl = fileName;		
		try {
			File initialFile = new File(imageUrl);
			if ( !initialFile.exists()){
				logger.error("File not found on Server for building Id "+buildingId);
				return SvcStatus.GET_FAILURE("File not found, Contact system administrator");
			}
			InputStream stream = new FileInputStream(initialFile);
			
			data.put("stream", stream);
			return SvcStatus.GET_SUCCESS("Success");
		} 
		catch (IOException e) {
			
			logger.error("Exception Found While getting building image due to: "+e);
			return SvcStatus.GET_FAILURE("Error in getting building image "+e);
		}
	}

	public Map<String, Object> addBuildingRoom(String building) 
	{
		BuildingRooms model = new BuildingRooms();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(building, BuildingRooms.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception Occured in saving Building Room");
		}
		if(model.getRoomId() == 0)
			return dao.saveBuildingRoom(model);
		else
			return dao.updateBuildingRoom(model);
	}
}
