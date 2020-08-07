package kmsg.sms.fees.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kmsg.sms.fees.model.ClassFeeStudents;

public class FeeClassStudentMapper implements RowMapper<ClassFeeStudents>
{
	@Override
	public ClassFeeStudents mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		ClassFeeStudents model = new ClassFeeStudents();
		model.setStudentId(rs.getInt("student_id"));
		model.setStudent(rs.getString("student"));
		model.setAmount(rs.getInt("amount"));
		model.setGender(rs.getString("gender"));
		model.setDob(rs.getString("dob"));
		model.setAge(rs.getInt("age"));
		model.setSection(rs.getString("section"));
		model.setSibling(rs.getString("sibling"));
		model.setBlocked(rs.getBoolean("blocked"));
		model.setFeeHeadId(rs.getInt("fee_head_id"));
		model.setFeeFrequency(rs.getString("fee_frequency"));
		model.setCollectionFrequency(rs.getString("collection_frequency"));
		model.setRefundable(rs.getBoolean("refundable"));
		model.setLateFeeFixAmt(rs.getInt("late_fee_fix_amt"));
		model.setLateFeeAmtPerDay(rs.getInt("late_fee_amt_per_day"));
		model.setLateFeePctPerDay(rs.getInt("late_fee_pct_per_day"));
		model.setLateFeeLimit(rs.getInt("late_fee_limit"));
		model.setCollectionTypeId(rs.getInt("collection_type_id"));
		model.setCollectionType(rs.getString("collection_type"));
		model.setCollectionDueDay(rs.getString("collection_due_day"));
		model.setCollectionDueMonth(rs.getString("collection_due_month"));
		model.setCollectionEventId(rs.getInt("collection_event_id"));
		model.setDiscountSiblingAmt(rs.getInt("discount_sibling_amt"));
		model.setDiscountSiblingPct(rs.getInt("discount_sibling_pct"));
		model.setDiscountScholarAmt(rs.getInt("discount_scholar_amt"));
		model.setDiscountScholarPct(rs.getInt("discount_scholar_pct"));
		model.setDiscountOtherLabel(rs.getString("discount_other_label"));
		model.setDiscountOtherAmt(rs.getInt("discount_other_amt"));
		model.setDiscountOtherPct(rs.getInt("discount_other_pct"));

		return model;
	}
}
