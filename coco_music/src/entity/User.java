package entity;

import annotation.Column;
import annotation.JsonFormat;
import java.sql.Date;

public class User {
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="user_id")
	private String user_id;
	@Column(name="password")
	private String password;
	private String head_portrait;
	private String sex;
	private String introduction;
//	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date birthday;
	/*private String birthday1;
	private java.util.Date birthday2;*/


	/*public String getBirthday1() {
		return birthday1;
	}
	public void setBirthday1(String birthday1) {
		this.birthday1 = birthday1;
	}
	public java.util.Date getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(java.util.Date birthday2) {
		this.birthday2 = birthday2;
	}*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHead_portrait() {
		return head_portrait;
	}

	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
