package kmsg.sms.mst.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import kmsg.sms.mst.model.DocTypeModel;

public class DocTypeMapper implements RowMapper<DocTypeModel> {

	@Override
	public DocTypeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return new DocTypeModel(rs.getInt("mst_doc_type_id"),rs.getString("doc_type_label"));
	}

}
