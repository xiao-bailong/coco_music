package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Table;

/**
 * 图书分类实体
 *
 *
 */
@Table(tableName = "recommand")
public class Recommand extends BaseEntity {
	private String univname;
	private String majorname;
	private int rate;
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
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}


}
