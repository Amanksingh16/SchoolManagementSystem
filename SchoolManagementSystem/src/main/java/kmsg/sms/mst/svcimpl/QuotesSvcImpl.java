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
import kmsg.sms.mst.adapter.QuotesAdapter;
import kmsg.sms.mst.svcint.QuotesSvcInt;
import kmsg.sms.redis.session.RedisSession;

@RestController
@RequestMapping("/sms/mst/quo")
public class QuotesSvcImpl implements QuotesSvcInt
{
	@Autowired
	QuotesAdapter adapter;
	
	@Autowired
	RedisSession ses;
	
	@Override
	@RequestMapping(value="/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getQuoteList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.listQuote();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveQuote(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String quote = params.get("quote");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.saveQuote(quote);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/items/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getQuoteItemList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String quoteId = params.get("quoteId");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.listQuoteItems(quoteId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/items/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveQuoteItem(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String quoteItem = params.get("quoteItem");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.saveQuoteItem(quoteItem);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/supplier/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getQuoteSupplierList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String quoteId = params.get("quoteId");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.listQuoteSupplier(quoteId);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/supplier/save", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> saveQuoteSupplier(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String quoteSupplier = params.get("quoteSupplier");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.saveQuoteSupplier(quoteSupplier);
		}
		return map;
	}
	
	@Override
	@RequestMapping(value="/supplier/items/list", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getQuoteSupplierItemList(@RequestParam Map<String, String> params, HttpSession session,HttpServletRequest request, HttpServletResponse response) 
	{
		Map<String,Object> map = new HashMap<>();

		map = ses.validateSchoolSession(session.getId(),request.getHeader("tokenId"));
		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{	
			response.setHeader("tokenId", (String)map.get("tokenId"));
			String quoteSupplierId = params.get("quoteSupplierId");
			int schoolId=(int) map.get("schoolId");
			adapter.setSchoolId(schoolId);
			return adapter.listQuoteSupplierItemList(quoteSupplierId);
		}
		return map;
	}
}
