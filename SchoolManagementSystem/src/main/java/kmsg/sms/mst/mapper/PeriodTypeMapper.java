package kmsg.sms.mst.mapper;

import kmsg.sms.mst.model.PeriodType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodTypeMapper implements RowMapper<PeriodType> {
	
		@Override
		public PeriodType mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			PeriodType model = new PeriodType();
		
		  model.setPeriodTypeId(rs.getInt("mst_period_type_id"));
		  model.setPeriodType(rs.getString("period_type"));
		  model.setAssignTeacher(rs.getBoolean("assign_teacher"));
		 
			return model;
		}

	

}
