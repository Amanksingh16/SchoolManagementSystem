package kmsg.sms.mst.model;

public class Roles {
	private int roleId;
	private String role;
	private String roleDesc;

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public Roles (int roleId, String role, String roleDesc) {
		this.roleId = roleId ;
		this.role = role;
		this.roleDesc = roleDesc;
	}

}
