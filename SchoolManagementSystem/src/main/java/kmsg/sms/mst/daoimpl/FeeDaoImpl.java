package kmsg.sms.mst.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kmsg.sms.common.Constants;
import kmsg.sms.common.SMSLogger;
import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoint.FeeDaoInt;
import kmsg.sms.mst.mapper.ClassFeesMapper;
import kmsg.sms.mst.mapper.FeeClassHeadMapper;
import kmsg.sms.mst.mapper.FeeClassStudentMapper;
import kmsg.sms.mst.mapper.FeeHeadCollectionMapper;
import kmsg.sms.mst.mapper.FeeHeadMapper;
import kmsg.sms.mst.model.ClassFeeHead;
import kmsg.sms.mst.model.ClassFeeStudents;
import kmsg.sms.mst.model.ClassFees;
import kmsg.sms.mst.model.FeeCollections;
import kmsg.sms.mst.model.FeeHead;

@Repository
public class FeeDaoImpl implements FeeDaoInt, SMSLogger
{
	private int schoolId;
	
	@Autowired
	JdbcTemplate template;	
	
	@Override
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public Map<String, Object> selectFeeHeads() 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " SELECT fee_head_id," + 
				" fee_head_code," + 
				" fee_frequency," + 
				" collection_frequency," + 
				" default_amt," + 
				" label," + 
				" refundable," + 
				" late_fee_limit," + 
				" late_fee_fix_amt," + 
				" late_fee_amt_per_day," + 
				" late_fee_pct_per_day," + 
				" discount_scholar_amt," + 
				" discount_scholar_pct," + 
				" discount_sibling_amt," + 
				" discount_sibling_pct," + 
				" discount_other_amt," + 
				" discount_other_pct," + 
				" IFNULL(discount_other_label,'') as discount_other_label," + 
				" collection_event_id," + 
				" mc.collection_type_id," + 
				" mc.collection_type," + 
				" IFNULL(collection_due_day,'') as collection_due_day," + 
				" IFNULL(collection_due_month,'') as collection_due_month" + 
				" FROM " + 
				+schoolId+"_fee_head fh "+
				" LEFT JOIN "+schoolId+"_mst_collection mc ON fh.collection_type_id = mc.collection_type_id;";
				
		try {
			List<FeeHead> list = template.query(SQL,new FeeHeadMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Exist");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstFeeHead",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}
	
	@Override
	public Map<String, Object> saveFeeHead(FeeHead model) {

		final String SQL = "INSERT INTO "+schoolId+"_fee_head(fee_head_code,label,refundable,late_fee_fix_amt,late_fee_amt_per_day,late_fee_pct_per_day,late_fee_limit,collection_type_id,collection_due_day,collection_due_month,collection_event_id,discount_sibling_amt,discount_sibling_pct,discount_scholar_amt,discount_scholar_pct,discount_other_label,discount_other_amt,discount_other_pct,fee_frequency,collection_frequency,default_amt) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		 int count = 0;
			KeyHolder holder = new GeneratedKeyHolder();
			try {
				 count = template.update (
					new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(
														SQL,
														Statement.RETURN_GENERATED_KEYS
																		);
							ps.setString( 1, model.getFeeHeadCode());
							ps.setString( 2, model.getLabel());
							ps.setBoolean( 3, model.isRefundable());
							ps.setInt( 4, model.getLateFeeFixAmt());
							ps.setInt( 5, model.getLateFeeAmtPerDay());
							ps.setInt( 6, model.getLateFeePctPerDay());
							ps.setInt( 7, model.getLateFeeLimit());
							ps.setInt( 8, model.getCollectionTypeId());
							ps.setString( 9, model.getCollectionDueDay());
							ps.setString( 10, model.getCollectionDueMonth());
							ps.setInt( 11, model.getCollectionEventId());
							ps.setInt(12, model.getDiscountSiblingAmt());
							ps.setInt(13, model.getDiscountSiblingPct());
							ps.setInt(14, model.getDiscountScholarAmt());
							ps.setInt(15, model.getDiscountScholarPct());
							ps.setString(16, model.getDiscountOtherLabel());
							ps.setInt(17, model.getDiscountOtherAmt());
							ps.setInt(18, model.getDiscountOtherPct());
							ps.setString(19, model.getFeeFrequency());
							ps.setString(20, model.getCollectionFrequency());
							ps.setInt(21, model.getDefaultAmt());
								
							return ps ;
						}
					}, holder ) ;
			}
			catch(DuplicateKeyException e) {
				logger.error("insertFeehead : Duplicate Key insertFeehead:" + model.getFeeHeadId());
				return SvcStatus.GET_FAILURE("FeeHead already exist" +  model.getFeeHeadId());
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("insertFeehead :Exception occured in insertFeehead:" + model.getFeeHeadId() + ": " + e.getMessage());
				return SvcStatus.GET_FAILURE("Error occured in adding FeeHead. Contact System Admin");
			}
			
			if (count > 0 ) {
				Map<String, Object> data = new HashMap<>();
				data.put("feeHeadId",holder.getKey().intValue());
				data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
				return data;
			}
			else {
				return SvcStatus.GET_FAILURE("FeeHead could not be added. Contact System Admin");
			}
	}

	@Override
	public Map<String, Object> updateFeeHead(FeeHead model) 
	{	
		final String SQL = "UPDATE "+schoolId+"_fee_head SET fee_head_code = ? ,"
				+ "label=?,"
				+ "refundable=?,"
				+ "late_fee_fix_amt=?,"
				+ "late_fee_amt_per_day=?,"
				+ "late_fee_pct_per_day=?,"
				+ "late_fee_limit=?,"
				+ "collection_type_id=?,"
				+ "collection_due_day=?,"
				+ "collection_due_month=?,"
				+ "collection_event_id=?,"
				+ "discount_sibling_amt=?,"
				+ "discount_sibling_pct=?,"
				+ "discount_scholar_amt=?,"
				+ "discount_scholar_pct=?,"
				+ "discount_other_label=?,"
				+ "discount_other_amt=?,"
				+ "discount_other_pct=?,"
				+ "fee_frequency=?,"
				+ "collection_frequency=?,"
				+ "default_amt=?"
				+ " WHERE fee_head_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
						model.getFeeHeadCode(),
						model.getLabel(),
						model.isRefundable(),
						model.getLateFeeFixAmt(),
						model.getLateFeeAmtPerDay(),
						model.getLateFeePctPerDay(),
						model.getLateFeeLimit(),
						model.getCollectionTypeId(),
						model.getCollectionDueDay(),
						model.getCollectionDueMonth(),
						model.getCollectionEventId(),
						model.getDiscountSiblingAmt(),
						model.getDiscountSiblingPct(),
						model.getDiscountScholarAmt(),
						model.getDiscountScholarPct(),
						model.getDiscountOtherLabel(),
						model.getDiscountOtherAmt(),
						model.getDiscountOtherPct(),
						model.getFeeFrequency(),
						model.getCollectionFrequency(),
						model.getDefaultAmt(),
						model.getFeeHeadId()
				} );
		}
		catch(DuplicateKeyException e) {
			logger.error("updateFeeHead : Duplicate Key for this FeeHead:" + model.getFeeHeadCode());
			return SvcStatus.GET_FAILURE("FeeHead already exist");
		}
		catch(Exception e) {
			logger.error("updateFeeHead :Exception occured in updateFeeHead:" + model.getFeeHeadCode() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in updating FeeHead. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "FeeHead Updated");
			return data;
		}
		else {
			logger.error("updateFeeHead : Failed to update FeeHead :" +  model.getFeeHeadCode());
			return SvcStatus.GET_FAILURE("FeeHead could not be updated. Contact System Admin");
		}
	}

	@Override
	public Map<String, Object> selectClassFeeHeads(int classId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT fee_class_id,"
				+ " fh.fee_head_id,"
				+ " fee_head_code,"
				+ " fee_frequency,"
				+ " collection_frequency,"
				+ " label,"
				+ " amount"
				+ " FROM "+schoolId+"_fee_class_head fch"
				+ " JOIN "+schoolId+"_fee_head fh ON fh.fee_head_id = fch.fee_head_id"
				+ " WHERE class_id = ?";
				
		try {
			List<ClassFeeHead> list = template.query(SQL,new Object[]{classId}, new FeeClassHeadMapper());
			
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstFeeClassHead",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	@Override
	public Map<String, Object> insertClassFeeHead(ClassFeeHead model, int classId) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_fee_class_head(class_id,fee_head_id,amount) VALUES (?,?,?)";

		int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			count = template.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
					
					ps.setInt(1, classId);
					ps.setInt(2, model.getFeeHeadId());
					ps.setInt(3, model.getAmount());
					
					return ps;
				}
			}, holder);
		} catch (Exception e) {
			logger.error("insertFeeClassHead:Exception occured: " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in saving FeeClassHead. Contact System Admin");
		}

		if (count > 0) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Class FeeHeads Saved");
			return data;
		} else {
			return SvcStatus.GET_FAILURE("Class FeeHeads could not be saved. Contact System Admin");
		}
	}
	
	@Override
	public Map<String, Object> DeleteClassFeeHead(int classId)
	{
		final String SQL = "DELETE FROM "+schoolId+"_fee_class_head"
				+ " WHERE class_id = ?";

	    int count = 0;
		try {
			 count = template.update(SQL,new Object[] {classId});
		}
		catch(Exception e) {
			logger.error("deleteClassFeeHead :Exception occured in deleteClassFeeHead for classId :" + classId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in deleting Class FeeHead. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Class FeeHead Deleted");
			return data;
		}
		else {
			logger.error("deleteClassFeeHead : Failed to delete FeeHead :" +  classId);
			return SvcStatus.GET_FAILURE("Class FeeHead could not be deleted. Contact System Admin");
		}		
	}

	@Override
	public Map<String, Object> DeleteFeeHead(int feeHeadId) 
	{
		final String SQL = "DELETE FROM "+schoolId+"_fee_head"
				+ " WHERE fee_head_id = ?";

	    int count = 0;
		try {
			 count = template.update(SQL,new Object[] {feeHeadId});
		}
		catch(Exception e) {
			logger.error("deleteFeeHead :Exception occured in deleteFeeHead:" + feeHeadId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in deleting FeeHead. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "FeeHead Deleted");
			return data;
		}
		else {
			logger.error("deleteFeeHead : Failed to delete FeeHead :" +  feeHeadId);
			return SvcStatus.GET_FAILURE("FeeHead could not be deleted. Contact System Admin");
		}		
	}

	@Override
	public Map<String, Object> selectClassFees(int wingId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		final String SQL = 
				"  	SELECT mc.class_id, " + 
				"	mc.class_name," + 
				"	SUM(fch.amount) as total_fees" + 
				"    FROM mst_class mc" + 
				"	JOIN (SELECT from_std_id, to_std_id FROM "+schoolId+"_wings WHERE wing_id = ?) as w " + 
				"    LEFT JOIN "+schoolId+"_fee_class_head fch ON fch.class_id = mc.class_id" + 
				"	WHERE mc.class_id BETWEEN w.from_std_id AND w.to_std_id" + 
				"    GROUP BY mc.class_id;" ;
				
		try {
			List<ClassFees> list = template.query(SQL,new Object[] {wingId},new ClassFeesMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No feehead class available");
				return data;
			}
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstFeeClass",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	public int checkClassFeeHeads(int classId) 
	{
		String SQL = "SELECT count(*) FROM "+schoolId+"_fee_class_head WHERE class_id = ?";
		try {
			return template.queryForObject(SQL, new Object[] {classId},Integer.class);
		}
		catch(Exception e)
		{
			logger.error("Something went wrong in checking class Fee Heads "+e);
			return -1;
		}
	}

	public Map<String, Object> selectFeeClassStudents(int classId)
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " SELECT" + 
				"   s.student_id" + 
				" , s.student" + 
				" , s.gender" + 
				" , DATE_FORMAT(s.dob,'dd-MM-yyyy') as dob" + 
				" , s.age" + 
				" , s.section" + 
				" , s.blocked" + 
				" , ss.sibling" + 
				" , s.fee_head_id" + 
				" , s.amount" + 
				" , s.refundable" + 
				" , s.late_fee_limit  " + 
				" , s.late_fee_fix_amt  " + 
				" , s.late_fee_amt_per_day  " + 
				" , s.late_fee_pct_per_day  " + 
				" , s.discount_scholar_amt  " + 
				" , s.discount_scholar_pct  " + 
				" , s.discount_sibling_amt  " + 
				" , s.discount_sibling_pct  " + 
				" , s.discount_other_amt " + 
				" , s.discount_other_pct  " + 
				" , IFNULL(s.discount_other_label,'') as discount_other_label" + 
				" , s.collection_event_id" + 
				" , mc.collection_type_id  " + 
				" , mc.collection_type" + 
				" , s.fee_frequency" + 
				" , s.collection_frequency" + 
				" , IFNULL(s.collection_due_day,'') as collection_due_day " + 
				" , IFNULL(s.collection_due_month,'') as collection_due_month  " + 
				" FROM 1_student s" + 
				" LEFT JOIN 1_student_sibling ss ON ss.student_id = s.student_id" + 
				" LEFT JOIN "+schoolId+"_mst_collection mc ON mc.collection_type_id = s.collection_type_id" + 
				" WHERE s.cls_id = ?" + 
				" ORDER BY s.student";
				
		try {
			List<ClassFeeStudents> list = template.query(SQL,new Object[]{classId}, new FeeClassStudentMapper());
			
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstStudentFeeHead",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	public Map<String, Object> insertFeeHeadCollections(FeeCollections i, int feeHeadId) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_fee_head_collections(fee_head_id,month,date,amount,remarks) VALUES(?,?,?,?,?)";

	    int count = 0;
		try {
			 count = template.update(SQL,new Object[] {feeHeadId,
					 i.getMonth(),
					 i.getDate(),
					 i.getAmount(),
					 i.getRemarks()});
		}
		catch(Exception e) {
			logger.error("insertFeeHeadCollection :Exception occured in insertFeeHeadCollection:" + feeHeadId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in inserting FeeHead Collection. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("insertFeeHeadCollection : Failed to insert FeeHead Collection:" +  feeHeadId);
			return SvcStatus.GET_FAILURE("FeeHead Collection could not be inserted. Contact System Admin");
		}		
	}

	public Map<String, Object> deleteFeeHeadCollection(int feeHeadId) 
	{
		final String SQL = "DELETE FROM "+schoolId+"_fee_head_collection"
				+ " WHERE fee_head_id = ?";

		try {
			 template.update(SQL,new Object[] {feeHeadId});
		}
		catch(Exception e) {
			logger.error("deleteFeeHeadCollection :Exception occured in deleteFeeHeadCollection:" + feeHeadId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in deleting FeeHead Collections. Contact System Admin");
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		return data;
	}
	
	public Map<String, Object> insertClassFeeHeadCollections(FeeCollections i, int feeHeadId, int classId) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_class_fee_head_collections(class_id,fee_head_id,month,date,amount,remarks) VALUES(?,?,?,?,?,?)";

	    int count = 0;
		try {
			 count = template.update(SQL,new Object[] {
					 classId,
					 feeHeadId,
					 i.getMonth(),
					 i.getDate(),
					 i.getAmount(),
					 i.getRemarks()});
		}
		catch(Exception e) {
			logger.error("insertClassFeeHeadCollection :Exception occured in insertClassFeeHeadCollection:" + feeHeadId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in inserting Class FeeHead Collection. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("insertClassFeeHeadCollection : Failed to insert Class FeeHead Collection:" +  feeHeadId);
			return SvcStatus.GET_FAILURE("Class FeeHead Collection could not be inserted. Contact System Admin");
		}		
	}

	public Map<String, Object> DeleteClassFeeHeadCollections(int feeHeadId) 
	{
		final String SQL = "DELETE FROM "+schoolId+"_class_fee_head_collection"
				+ " WHERE class_id = ?";

	    try {
			 template.update(SQL,new Object[] {feeHeadId});
		}
		catch(Exception e) {
			logger.error("deleteClassFeeHeadCollection :Exception occured in deleteClassFeeHeadCollection:" + feeHeadId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in deleting Class FeeHead Collections. Contact System Admin");
		}
	    
		Map<String, Object> data = new HashMap<>();
		data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
		return data;		
	}
	
	public Map<String, Object> insertStudentFeeHeadCollections(FeeCollections i, int feeHeadId, int studentId) 
	{
		final String SQL = "INSERT INTO "+schoolId+"_student_fee_head_collections(student_id,fee_head_id,month,date,amount,remarks) VALUES(?,?,?,?,?,?)";

	    int count = 0;
		try {
			 count = template.update(SQL,new Object[] {
					 studentId,
					 feeHeadId,
					 i.getMonth(),
					 i.getDate(),
					 i.getAmount(),
					 i.getRemarks()});
		}
		catch(Exception e) {
			logger.error("insertStudentFeeHeadCollection :Exception occured in insertStudentFeeHeadCollection:" + feeHeadId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in inserting Student FeeHead Collection. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("insertStudentFeeHeadCollection : Failed to insert Student FeeHead Collection:" +  feeHeadId);
			return SvcStatus.GET_FAILURE("Student FeeHead Collection could not be inserted. Contact System Admin");
		}		
	}

	public Map<String, Object> DeleteStudentFeeHeadCollections(int studentId) 
	{
		final String SQL = "DELETE FROM "+schoolId+"_student_fee_head_collection"
				+ " WHERE student_id = ?";

	    int count = 0;
		try {
			 count = template.update(SQL,new Object[] {studentId});
		}
		catch(Exception e) {
			logger.error("deleteStudentFeeHeadCollection :Exception occured in deleteStudentFeeHeadCollection:" + studentId + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in deleting Class FeeHead Collections. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			return data;
		}
		else {
			logger.error("deleteStudentFeeHeadCollection : Failed to delete Student FeeHead Collections:" +  studentId);
			return SvcStatus.GET_FAILURE("Student FeeHead Collections could not be deleted. Contact System Admin");
		}		
	}

	public Map<String, Object> selectFeeHeadCollections(int feeHeadId) 
	{
		Map<String,Object> data = new HashMap<>();
		
		final String SQL = 
				"  	SELECT month,"
				+ " date,"
				+ " amount,"
				+ " remarks "
				+ " FROM "+schoolId+"_fee_head_collection" + 
				"   WHERE fee_head_id = ?" ;
				
		try {
			List<FeeCollections> list = template.query(SQL,new Object[] {feeHeadId},new FeeHeadCollectionMapper());
			
			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("feeCollections",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	public Map<String, Object> updateStudentFeeHead(ClassFeeStudents model) 
	{
		final String SQL = "UPDATE "+schoolId+"_student SET fee_head_id = ?,"
				+ "label=?,"
				+ "refundable=?,"
				+ "late_fee_fix_amt=?,"
				+ "late_fee_amt_per_day=?,"
				+ "late_fee_pct_per_day=?,"
				+ "late_fee_limit=?,"
				+ "collection_type_id=?,"
				+ "collection_due_day=?,"
				+ "collection_due_month=?,"
				+ "collection_event_id=?,"
				+ "discount_sibling_amt=?,"
				+ "discount_sibling_pct=?,"
				+ "discount_scholar_amt=?,"
				+ "discount_scholar_pct=?,"
				+ "discount_other_label=?,"
				+ "discount_other_amt=?,"
				+ "discount_other_pct=?,"
				+ "fee_frequency=?,"
				+ "collection_frequency=?,"
				+ "amount=?"
				+ " WHERE student_id = ?";

	    int count = 0;
		try {
			 count = template.update (SQL, new Object[]
				 {
						model.getFeeHeadId(),
						model.getLabel(),
						model.isRefundable(),
						model.getLateFeeFixAmt(),
						model.getLateFeeAmtPerDay(),
						model.getLateFeePctPerDay(),
						model.getLateFeeLimit(),
						model.getCollectionTypeId(),
						model.getCollectionDueDay(),
						model.getCollectionDueMonth(),
						model.getCollectionEventId(),
						model.getDiscountSiblingAmt(),
						model.getDiscountSiblingPct(),
						model.getDiscountScholarAmt(),
						model.getDiscountScholarPct(),
						model.getDiscountOtherLabel(),
						model.getDiscountOtherAmt(),
						model.getDiscountOtherPct(),
						model.getFeeFrequency(),
						model.getCollectionFrequency(),
						model.getAmount(),
						model.getFeeHeadId()
				} );
		}
		catch(Exception e) {
			logger.error("updateStudentFeeHead :Exception occured in updateStudentFeeHead:" + model.getFeeHeadId() + ": " + e.getMessage());
			return SvcStatus.GET_FAILURE("Error occured in saving Student FeeHead. Contact System Admin");
		}
		
		if (count > 0 ) {
			Map<String, Object> data = new HashMap<>();
			data.put(SvcStatus.STATUS, SvcStatus.SUCCESS);
			data.put(SvcStatus.MSG, "Student FeeHead Saved");
			return data;
		}
		else {
			logger.error("updateStudentFeeHead : Failed to update Student FeeHead :" +  model.getFeeHeadId());
			return SvcStatus.GET_FAILURE("Student FeeHead could not be saved. Contact System Admin");
		}
	}
}
