package kmsg.sms.mst.svcimpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.adapter.SupplierAdapter;
import kmsg.sms.mst.svcint.SupplierSvcInt;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/supplier")
public class SupplierSvcImpl implements SupplierSvcInt{
	
	@Autowired
	SupplierAdapter adapter;
	
	@Autowired
	RedisSession ses;

	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getSupplierList(Map<String, String> params, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.getSupplierList();
		}
		return map;
	}

	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveSupplier(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int) map.get("schoolId");
			String supplier = params.get("supplier");
			adapter.setSchoolId(schoolId);
			return adapter.addSupplier(supplier);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/generatepwd", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> GenerateSupplierPassword(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId = (int) map.get("schoolId");
			String email = params.get("email");
			adapter.setSchoolId(schoolId);
			return adapter.generateSupplierPassword(email);
		}
		return map;
	}
}
