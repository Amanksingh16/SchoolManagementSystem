package kmsg.sms.mst.svcimpl;

import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.adapter.BuildingAdapter;
import kmsg.sms.mst.svcint.BuildingSvcInt;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/a/building")
public class BuildingSvcImpl implements BuildingSvcInt,SMSLogger
{

	@Autowired
	BuildingAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getBuildingList(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			return adapter.getBuildingList();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> BuildingSave(@RequestParam(value = "buildingFile",required=false) MultipartFile file,@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String building = params.get("building");
			return adapter.addBuilding(building,file);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/viewimg", method = RequestMethod.GET, headers="Accept=application/json")
	public Map<String, Object> ViewBuildingImage(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
	
		String buildingId = params.get("buildingId");
		try
		{
			map = adapter.getBuildingImg(Integer.parseInt(buildingId));
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
			{
				InputStream stream = (InputStream) map.get("stream");
				IOUtils.copy(stream, response.getOutputStream());
				return null ;
			}
			return map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception Occured in getting Image for building ID "+buildingId);
			map.put(SvcStatus.STATUS,SvcStatus.FAILURE);
			map.put(SvcStatus.MSG,"Exception Occured in getting building Image");
			return map;
		}

	}
	
	@Override
	@RequestMapping(value="/rooms/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getBuildingRoomsList(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		String buildingId = params.get("buildingId");

		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			return adapter.getBuildingRoomsList(Integer.parseInt(buildingId));
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/rooms/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> BuildingRoomsSave(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		Map<String,Object> map = new HashMap<>();
		String CurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		map = ses.validateSchoolSession(httpSession.getId(), CurrMethod);
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			String building = params.get("buildingRoom");
			return adapter.addBuildingRoom(building);
		}
		return map;
	}
}
