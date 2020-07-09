package kmsg.sms.mst.model;

	public class PeriodType {
		private int periodTypeId;
		private String periodType;
		private boolean assignTeacher;
		
		public int getPeriodTypeId() {
			return periodTypeId;
		}
		public void setPeriodTypeId(int periodTypeId) {
			this.periodTypeId = periodTypeId;
		}
		public String getPeriodType() {
			return periodType;
		}
		public void setPeriodType(String periodType) {
			this.periodType = periodType;
		}
		public boolean isAssignTeacher() {
			return assignTeacher;
		}
		public void setAssignTeacher(boolean assignTeacher) {
			this.assignTeacher = assignTeacher;
		}

		public PeriodType(int periodTypeId, String periodType, boolean assignTeacher) {
			this.periodTypeId = periodTypeId;
			this.periodType = periodType;
			this.assignTeacher = assignTeacher;
		}
		public PeriodType() {
			super();
		}	
		
	}

