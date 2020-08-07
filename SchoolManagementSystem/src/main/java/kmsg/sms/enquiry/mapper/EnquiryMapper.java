package kmsg.sms.enquiry.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.common.EnquiryStatus;
import kmsg.sms.common.EnquiryType;
import kmsg.sms.enquiry.model.Enquiry;
import kmsg.sms.mst.model.Classes;

public class EnquiryMapper implements RowMapper<Enquiry> {

	@Override
	public Enquiry mapRow(ResultSet rs, int rowNum) throws SQLException {
		Enquiry model = new Enquiry();
		model.setEnquiryId(rs.getInt("enquiry_id"));
		model.setEnquiryNo(rs.getString("enquiry_no"));
		model.setEnquiry(rs.getString("enquiry"));
		model.setEnquiryDt(rs.getString("enquiry_dt"));
		model.setGender(rs.getString("gender"));
		model.setAge(rs.getInt("age"));
		model.setContactNo(rs.getString("contact_no"));
		model.seteMail(rs.getString("email"));
		model.setSourceId(rs.getInt("source_id"));
		model.setSource(EnquiryType.get(rs.getInt("source_id")));
		model.setClasses(new Classes(rs.getInt("class_id"), rs.getString("class_name")));
		model.setAssignedToId(rs.getInt("assigned_to_id"));
		model.setAssignedTo(rs.getString("teacher"));
		model.setAssignedOn(rs.getString("assigned_dt"));
		model.setStatusId( rs.getInt("status_id"));
		model.setStatus( EnquiryStatus.get( rs.getInt("status_id")));
		model.setQuery( rs.getString("qry"));
		model.setRegsNo( rs.getString("regs_no"));
		return model;
	}

}
