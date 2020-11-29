package user.member;

public class TalkMember {
	/* name, t_uid, pass, email, phone, filename
	member_id NUMBER PRIMARY KEY,
	name varchar2(20),
	t_uid varchar2(20),
	pass varchar2(20),
	email varchar2(50),
	phone varchar2(20),
	filename varchar2(1000)
	*/
	private int member_id;
	private String name;
	private String t_uid;
	private String pass;
	private String email;
	private String phone;
	private String filename;
	
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getT_uid() {
		return t_uid;
	}
	public void setT_uid(String t_uid) {
		this.t_uid = t_uid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
}
