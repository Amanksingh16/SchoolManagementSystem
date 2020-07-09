package kmsg.sms.mst.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kmsg.sms.mst.daoint.DocTypeDaoInt;
import kmsg.sms.mst.mapper.DocTypeMapper;
import kmsg.sms.mst.model.DocTypeModel;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;


@Repository
public class DocTypeDaoImpl implements DocTypeDaoInt,SMSLogger{
	@Autowired
	JdbcTemplate template;	
	public Map<String, Object> getDocTypeList() {
		// TODO Auto-generated method stub
		final String SQL = 
				" SELECT mst_doc_type_id,"
				+ " IFNULL(doc_type,doc_type) as doc_type_label"
				+ " FROM "
				+ "mst_doc_type";
		
		List<DocTypeModel> list = new ArrayList<>();
		try {
			list = template.query(SQL, new DocTypeMapper());
			if(list.size() == 0)
			{
				logger.error("selectDocType: No DocType found");
				return SvcStatus.GET_FAILURE("No DocType found. Contact System admin");
			}
		} 
		catch (Exception e) {
			System.out.println(e);
			logger.error("selectDocType: Exception in selecting DocType " + e);
			return SvcStatus.GET_FAILURE("Error occured in selecting DocType. Contact System admin");
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		result.put("lstDocType",  list );
		return result ;	
	}

}
