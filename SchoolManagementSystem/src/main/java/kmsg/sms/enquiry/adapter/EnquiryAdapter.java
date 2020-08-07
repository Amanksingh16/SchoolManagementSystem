package kmsg.sms.enquiry.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.enquiry.daoimpl.EnquiryDaoImpl;
import kmsg.sms.enquiry.model.ClassWiseGraph;
import kmsg.sms.enquiry.model.Conversion;
import kmsg.sms.enquiry.model.Enquiry;
import kmsg.sms.enquiry.model.YearlyComparison;
import kmsg.sms.mst.adapter.WingsAdapter;
import kmsg.sms.mst.model.WingClass;

@Component
public class EnquiryAdapter implements SMSLogger 
{
	@Autowired
	WingsAdapter adapter;
	
	@Autowired
	EnquiryDaoImpl dao;	
	
	public void setSchoolId(int schoolId) {
		dao.setSchoolId(schoolId);
	}
	
	public Map<String, Object> getEnquiry() {
		return dao.selectEnquiry();
	}
	
	public Map<String, Object> saveEnquiry( String strEnquiry ) 
	{
		Enquiry model = new Enquiry();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue( strEnquiry, Enquiry.class);
		}
		catch(Exception e) {
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception in Enquiry data");
		}
		if(model.getEnquiryId() == 0)
			return dao.insertEnquiry(model);
		else
			return dao.updateEnquiry(model);
	}

	public Map<String, Object> registerEnquiry(String enquiryId,String regsNo) 
	{
		return dao.registerEnquiry(enquiryId,regsNo);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> ConversionGraph(String year) 
	{
		Map<String,Object> map = new HashMap<>();
		Conversion con = new Conversion();

		map = dao.ConversionGraph(year,"enquiry_dt");
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		con.setEnquiryData((List<Integer>) map.get("enquiryRegister"));
		
		map = dao.ConversionGraph(year,"regs_dt");
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		con.setEnquiryRegister((List<Integer>) map.get("enquiryRegister"));
		con.setWeekLabels(Constants.WEEKDAYS);
		
		map.clear();
		map.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		map.put( "conversion", con);
		return map ;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> YearComparisonGraph() 
	{
		Map<String,Object> map = new HashMap<>();
		List<Integer> years = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		
		YearlyComparison con = new YearlyComparison();
		
		map = dao.YearlyComparisonGraph(year);		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		con.setYear1((List<Integer>) map.get("yearData"));
		
		years.add(year);
		year = year - 1;
		
		map = dao.YearlyComparisonGraph(year);		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		con.setYear2((List<Integer>) map.get("yearData"));

		years.add(year);
		year = year - 1;
		
		map = dao.YearlyComparisonGraph(year);		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		con.setYear3((List<Integer>) map.get("yearData"));

		years.add(year);
		year = year - 1;
		
		map = dao.YearlyComparisonGraph(year);		
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		con.setYear4((List<Integer>) map.get("yearData"));

		years.add(year);
		
		con.setWeekLabels(Constants.WEEKDAYS);
		con.setYears(years);
		
		map.clear();
		map.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		map.put( "yearlyComparison", con);
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> ClassComparisonGraph(String wingId, int schoolId) 
	{
		Map<String,Object> map = new HashMap<>();
		ClassWiseGraph data = new ClassWiseGraph();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR) - 3;
		
		List<String> classes = new ArrayList<>();
		List<Integer> year1 = new ArrayList<>();
		List<Integer> year2 = new ArrayList<>();
		List<Integer> year3 = new ArrayList<>();
		List<Integer> years = IntStream.range(year, year+3).boxed().collect(Collectors.toList());
		
		adapter.setSchoolId(schoolId);
		map = adapter.getClasses(Integer.parseInt(wingId));
		if(map.get(SvcStatus.STATUS).equals(SvcStatus.FAILURE))
			return map;
		
		List<WingClass> wingClass = (List<WingClass>) map.get("lstWingClass");
		
		for(int i = 0; i < wingClass.size(); i++)
		{
			year = c.get(Calendar.YEAR) - 3;
			int year1val = dao.ClassWiseGraph(year,wingClass.get(i).getClassId());
			if(year1val == -1)
				return SvcStatus.GET_FAILURE("Error occured in getting ClassWise Data. Contact system admin") ;
			year1.add(year1val);			
			year = year + 1;
			int year2val = dao.ClassWiseGraph(year,wingClass.get(i).getClassId());
			if(year2val == -1)
				return SvcStatus.GET_FAILURE("Error occured in getting ClassWise Data. Contact system admin") ;
			year2.add(year2val);
			year = year + 1;
			int year3val = dao.ClassWiseGraph(year,wingClass.get(i).getClassId());
			if(year3val == -1)
				return SvcStatus.GET_FAILURE("Error occured in getting ClassWise Data. Contact system admin") ;
			year3.add(year3val);
			
			classes.add(wingClass.get(i).getClassName());
		}
		data.setClasses(classes);
		data.setYear1(year1);
		data.setYear2(year2);
		data.setYear3(year3);
		data.setYears(years);
		
		map.clear();
		map.put( SvcStatus.STATUS, SvcStatus.SUCCESS);
		map.put( "classWiseData",data);
		return map;
	}
}
