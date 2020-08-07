package kmsg.sms.enquiry.model;

import java.util.List;

public class YearlyComparison 
{
	private List<Integer> year1;
	private List<Integer> year2;
	private List<Integer> year3;
	private List<Integer> year4;
	private List<Integer> weekLabels;
	private List<Integer> years;
	
	public List<Integer> getYear1() {
		return year1;
	}
	public void setYear1(List<Integer> year1) {
		this.year1 = year1;
	}
	public List<Integer> getYear2() {
		return year2;
	}
	public void setYear2(List<Integer> year2) {
		this.year2 = year2;
	}
	public List<Integer> getYear3() {
		return year3;
	}
	public void setYear3(List<Integer> year3) {
		this.year3 = year3;
	}
	public List<Integer> getYear4() {
		return year4;
	}
	public void setYear4(List<Integer> year4) {
		this.year4 = year4;
	}
	public List<Integer> getWeekLabels() {
		return weekLabels;
	}
	public void setWeekLabels(List<Integer> weekLabels) {
		this.weekLabels = weekLabels;
	}
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
}
