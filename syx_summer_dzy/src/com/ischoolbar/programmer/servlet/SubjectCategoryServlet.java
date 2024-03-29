package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ischoolbar.programmer.dao.SubjectCategoryDao;
import com.ischoolbar.programmer.entity.SubjectCategory;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * 高校分类管理控制器
 *
 *
 */
public class SubjectCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5036792054338741106L;
	/**
	 * 
	 */


	private SubjectCategoryDao subjectCategoryDao = new SubjectCategoryDao();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Object attribute = request.getParameter("method");
		String method = "";
		if(attribute != null){
			method = attribute.toString();
		}
		if("toSubjectCategoryListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/subject_category.jsp").forward(request, response);
			return;
		}
		if("BookCategoryList".equals(method)){
			getBookCategoryList(request,response);
			return;
		}
		if("AddBookCategory".equals(method)){
			addBookCategory(request,response);
			return;
		}
		if("EditBookCategory".equals(method)){
			editBookCategory(request,response);
			return;
		}
		if("DeleteBookCategory".equals(method)){
			deleteBookCategory(request,response);
			return;
		}
	}

	private void deleteBookCategory(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		Map<String, String> ret = new HashMap<String, String>();
		if(ids == null || ids.length ==0){
			ret.put("type", "error");
			ret.put("msg", "请选中要删除的数据!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!subjectCategoryDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "删除失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void editBookCategory(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "分类名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		SubjectCategory subjectCategory = new SubjectCategory();
		subjectCategory.setName(name);
		subjectCategory.setId(id);
		if(!subjectCategoryDao.update(subjectCategory)){
			ret.put("type", "error");
			ret.put("msg", "分类更新失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "分类更新成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void addBookCategory(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "分类名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		SubjectCategory subjectCategory = new SubjectCategory();
		subjectCategory.setName(name);
		if(!subjectCategoryDao.add(subjectCategory)){
			ret.put("type", "error");
			ret.put("msg", "分类添加失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "分类添加成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void getBookCategoryList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<SubjectCategory> page = new Page<SubjectCategory>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("name", "%"+name+"%", Operator.LIKE));
		page = subjectCategoryDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	
}
