package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;
import com.ischoolbar.programmer.annotation.Table;

/**
 * 
 * 
 *
 */
@Table(tableName = "user_info")
public class UserInfo extends BaseEntity {
	private int userid;
	private String username;
	private String idnum;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Column(name="subj1",isForeignEntity=true)
	private SubjectCategory subj1;//选考科目1
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdnum() {
		return idnum;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	public SubjectCategory getSubj1() {
		return subj1;
	}
	public void setSubj1(SubjectCategory subj1) {
		this.subj1 = subj1;
	}
	public SubjectCategory getSubj2() {
		return subj2;
	}
	public void setSubj2(SubjectCategory subj2) {
		this.subj2 = subj2;
	}
	public SubjectCategory getSubj3() {
		return subj3;
	}
	public void setSubj3(SubjectCategory subj3) {
		this.subj3 = subj3;
	}
	public int getTotal_a() {
		return total_a;
	}
	public void setTotal_a(int total_a) {
		this.total_a = total_a;
	}
	public String getTotal_score() {
		return total_score;
	}
	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	@Column(name="subj2",isForeignEntity=true)
	private SubjectCategory subj2;//选考科目2
	@Column(name="subj3",isForeignEntity=true)
	private SubjectCategory subj3;//选考科目3
	private int total_a ;
	private int total_b;
	private int total_c;
	private int total_d;
	public int getTotal_b() {
		return total_b;
	}
	public void setTotal_b(int total_b) {
		this.total_b = total_b;
	}
	public int getTotal_c() {
		return total_c;
	}
	public void setTotal_c(int total_c) {
		this.total_c = total_c;
	}
	public int getTotal_d() {
		return total_d;
	}
	public void setTotal_d(int total_d) {
		this.total_d = total_d;
	}
	private String total_score ;
	private String specialty;

}
