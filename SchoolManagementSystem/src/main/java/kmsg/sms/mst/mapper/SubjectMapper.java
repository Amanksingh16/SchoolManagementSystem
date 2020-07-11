package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import kmsg.sms.mst.model.SubjectModel;

public class SubjectMapper implements RowMapper<SubjectModel> {

	@Override
	public SubjectModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return new SubjectModel(rs.getInt("mst_subject_id"),rs.getString("subject_label"));
	}

}
