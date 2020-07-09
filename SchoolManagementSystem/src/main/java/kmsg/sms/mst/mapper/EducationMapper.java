package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import kmsg.sms.mst.model.EducationModel;

public class EducationMapper implements RowMapper<EducationModel> {

	@Override
	public EducationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return new EducationModel(rs.getInt("mst_education_id"),rs.getString("education_label"));
	}

}
