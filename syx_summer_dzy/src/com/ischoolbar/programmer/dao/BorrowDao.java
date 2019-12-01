package com.ischoolbar.programmer.dao;

import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.Borrow;
import com.ischoolbar.programmer.entity.Enroll;
import com.ischoolbar.programmer.entity.User;

public class BorrowDao extends BaseDao<Borrow> {
	public List<Map<String, Object>> getStats(String type){
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		String sql = "";
		String sql1 = "";
		sql = "select count(id) as num,univname from enroll  GROUP BY univname ORDER BY num limit 0,20";
		try {
			PreparedStatement prepareStatement = getDbUtil().connection.prepareStatement(sql);
			ResultSet executeQuery = prepareStatement.executeQuery();
			while(executeQuery.next()){
				Map<String, Object> stats = new HashMap<String, Object>();
				Enroll enroll;
				Enroll enroll1;
				String univname = executeQuery.getString("univname");
				sql1 = "select sum(plannumber) as sum from major_info where univname = "+'"'+univname+'"';
				PreparedStatement prepareStatement2 = getDbUtil().connection.prepareStatement(sql1);
				ResultSet executeQuery2 = prepareStatement2.executeQuery();
				if("user".equals(type)){
					if(executeQuery2.next()){
						int  value=executeQuery2.getInt("sum")*100/executeQuery.getInt("num");
						if(value>100)
							stats.put("num",100);
						else{
							stats.put("num", value);
						}
						stats.put("name", executeQuery.getString("univname"));
					}
				}
				if("book".equals(type)){
					stats.put("num", executeQuery.getInt("num"));
					stats.put("name",  executeQuery.getString("univname"));
				}
				ret.add(stats);
			}
			closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
