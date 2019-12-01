package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;

public class MajorInfo extends BaseEntity {
	private String univname;
	private int anum;
	private int cyscore;
	
	public int getAnum() {
		return anum;
	}
	public int getCyscore() {
		return cyscore;
	}
	public void setCyscore(int cyscore) {
		this.cyscore = cyscore;
	}
	public void setAnum(int anum) {
		this.anum = anum;
	}
	private int univid;
	public int getUnivid() {
		return univid;
	}
	public void setUnivid(int univid) {
		this.univid = univid;
	}
	private String majorname;
	@Column(name="majortype_category",isForeignEntity=true)
	private MajortypeCategory majortypeCategory;//高校分类
	public String getUnivname() {
		return univname;
	}
	public void setUnivname(String univname) {
		this.univname = univname;
	}
	public String getMajorname() {
		return majorname;
	}
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	public MajortypeCategory getMajortypeCategory() {
		return majortypeCategory;
	}
	public void setMajortypeCategory(MajortypeCategory majortypeCategory) {
		this.majortypeCategory = majortypeCategory;
	}
	public int getPlannumber() {
		return plannumber;
	}
	public void setPlannumber(int plannumber) {
		this.plannumber = plannumber;
	}
	public String getResubjects() {
		return resubjects;
	}
	public void setResubjects(String resubjects) {
		this.resubjects = resubjects;
	}
	public String getMajorinfor() {
		return majorinfor;
	}
	public void setMajorinfor(String majorinfor) {
		this.majorinfor = majorinfor;
	}
	public String getTestsubjects() {
		return testsubjects;
	}
	public void setTestsubjects(String testsubjects) {
		this.testsubjects = testsubjects;
	}
	private int plannumber;
	private String resubjects;
	private String testsubjects;
	private String majorinfor;

}
