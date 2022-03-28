package com.gettoere.vo;

// 17.11.08 �α��� ktg
public class LoginVO {
	private int mNo;
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private int grade;
	private String msg;
	
	//개인정보수정에서 가져와야할것 추가 kyo 17.11.30
	private String phone;
	private String email;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	//본인이 해당되는 게시판 번호 추가. ssh. 171122
	private int lNoManager;
	
	//활동, 정지 상태 추가 . ssh. 171128
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getlNoManager() {
		return lNoManager;
	}
	public void setlNoManager(int lNoManager) {
		this.lNoManager = lNoManager;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getmNo() {
		return mNo;
	}
	public void setmNo(int mNo) {
		this.mNo = mNo;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
    public String toString() {
        return "LoginVO [id=" + id + ", pw=" + pw + ", name=" + name + ", nickname="
                + nickname + ", grade=" + grade + "]";
    }
}
