package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Table;

/**
 * ͼ�����ʵ��
 *
 *
 */
@Table(tableName = "majortype_category")
public class MajortypeCategory extends BaseEntity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
