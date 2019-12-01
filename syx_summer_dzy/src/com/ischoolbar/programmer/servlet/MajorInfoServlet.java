package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ischoolbar.programmer.dao.BookDao;
import com.ischoolbar.programmer.dao.MajorInfoDao;
import com.ischoolbar.programmer.dao.MajortypeCategoryDao;
import com.ischoolbar.programmer.dao.SubjectCategoryDao;
import com.ischoolbar.programmer.dao.UniversityCategoryDao;
import com.ischoolbar.programmer.dao.UserInfoDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.MajorInfo;
import com.ischoolbar.programmer.entity.MajortypeCategory;
import com.ischoolbar.programmer.entity.SubjectCategory;
import com.ischoolbar.programmer.entity.UniversityCategory;
import com.ischoolbar.programmer.entity.UserInfo;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * 用户管理控制器
 * 
 *
 */
public class MajorInfoServlet extends HttpServlet {
	private BookDao bookDao = new BookDao();
	private MajorInfoDao majorinfoDao  = new MajorInfoDao();
	private MajortypeCategoryDao majortypeCategoryDao = new MajortypeCategoryDao();
	private static final long serialVersionUID = 5677180878017698596L;
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
		if("toMajorInfoListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/major_info.jsp").forward(request, response);
			return;
		}
		if("MajorInfoList".equals(method)){
			getBookList(request,response);
			return;
		}
		if("AddBook".equals(method)){
			addBook(request,response);
			return;
		}
		if("EditBook".equals(method)){
			editBook(request,response);
			return;
		}
		if("DeleteBook".equals(method)){
			deleteBook(request,response);
			return;
		}
		if("GetBookCategoryComboxData".equals(method)){
			getBookCategoryComboxData(request,response);
			return;
		}
		
	}
	private void getBookCategoryComboxData(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Page<MajortypeCategory> page = new Page<MajortypeCategory>(1, 999);
		page = majortypeCategoryDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		MajortypeCategory majortypeCategory = new MajortypeCategory();
		majortypeCategory.setId(0);
		majortypeCategory.setName("全部");
		page.getContent().add(majortypeCategory);
		ret.put("type", "success");
		ret.put("values", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void deleteBook(HttpServletRequest request,
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
		if(!majorinfoDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "删除失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void editBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String univname = request.getParameter("univname");
		String majorname = request.getParameter("majorname");
		int cyscore = Integer.parseInt(request.getParameter("cyscore"));
		int anum = Integer.parseInt(request.getParameter("anum"));
		Book book  = bookDao.find(univname,"name");
		int majortypeCategoryId = Integer.parseInt(request.getParameter("majortypeCategoryId"));
		int plannumber = Integer.parseInt(request.getParameter("plannumber"));
		String resubjects = request.getParameter("resubjects");
		String testsubjects = request.getParameter("testsubjects");
		String majorinfor = request.getParameter("majorinfor");
//		String province  = request.getParameter("province");
//		String rank  = request.getParameter("rank");
//		String address  = request.getParameter("address");
//		String province  = request.getParameter("");
//		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(univname)){
			ret.put("type", "error");
			ret.put("msg", "图书名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		MajortypeCategory majortypeCategory = majortypeCategoryDao.find(majortypeCategoryId);

		if(majortypeCategory == null){
			ret.put("type", "error");
			ret.put("msg", "选考科目一不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		//检查图书总数是否大于借出的数
//		Book oldBook = bookDao.find(id);
		//已经借出的数量
//		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
//		if(number < borrowedNumber){
//			ret.put("type", "error");
//			ret.put("msg", "数量不能小于已经借出的数量!");
//			StringUtil.writrToPage(response,JSONObject.toJSONString(ret));
//			return;
//		}
		
		MajorInfo majorinfo = new MajorInfo();
		majorinfo.setId(id);
		majorinfo.setAnum(anum);
		majorinfo.setUnivname(univname);
		majorinfo.setMajorname(majorname);
		majorinfo.setCyscore(cyscore);
		majorinfo.setMajortypeCategory(majortypeCategory);
		majorinfo.setPlannumber(plannumber);
		majorinfo.setResubjects(resubjects);
		majorinfo.setTestsubjects(testsubjects);
		majorinfo.setMajorinfor(majorinfor);
		majorinfo.setUnivid(book.getId());
		
		if(!majorinfoDao.update(majorinfo)){
			ret.put("type", "error");
			ret.put("msg", "图书更新失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "图书更新成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String univname = request.getParameter("univname");
		String majorname = request.getParameter("majorname");
		int cyscore = Integer.parseInt(request.getParameter("cyscore"));
		Book book  = bookDao.find(univname,"name");
		int majortypeCategoryId = Integer.parseInt(request.getParameter("majortypeCategoryId"));
		int anum = Integer.parseInt(request.getParameter("anum"));
		int plannumber = Integer.parseInt(request.getParameter("plannumber"));
		String resubjects = request.getParameter("resubjects");
		String testsubjects = request.getParameter("testsubjects");
		String majorinfor = request.getParameter("majorinfor");
//		String province  = request.getParameter("province");
//		String rank  = request.getParameter("rank");
//		String address  = request.getParameter("address");
//		String province  = request.getParameter("");
//		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(univname)){
			ret.put("type", "error");
			ret.put("msg", "图书名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		MajortypeCategory majortypeCategory = majortypeCategoryDao.find(majortypeCategoryId);

		if(majortypeCategory == null){
			ret.put("type", "error");
			ret.put("msg", "选考科目一不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(!isExistProperty(univname,"name",0)){
			ret.put("type", "error");
			ret.put("msg", "该高校未注册三位一体系统!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
//		if(total_score == null){
//			ret.put("type", "error");
//			ret.put("msg", "选考科目三不能为空!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
//		if(specialty == null){
//			ret.put("type", "error");
//			ret.put("msg", "选考科目三不能为空!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
		//检查图书总数是否大于借出的数
//		Book oldBook = bookDao.find(id);
		//已经借出的数量
//		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
//		if(number < borrowedNumber){
//			ret.put("type", "error");
//			ret.put("msg", "数量不能小于已经借出的数量!");
//			StringUtil.writrToPage(response,JSONObject.toJSONString(ret));
//			return;
//		}
		
		MajorInfo majorinfo = new MajorInfo();
		majorinfo.setUnivname(univname);
		majorinfo.setMajorname(majorname);
		majorinfo.setMajortypeCategory(majortypeCategory);
		majorinfo.setAnum(anum);
		majorinfo.setPlannumber(plannumber);
		majorinfo.setResubjects(resubjects);
		majorinfo.setTestsubjects(testsubjects);
		majorinfo.setCyscore(cyscore);
		majorinfo.setMajorinfor(majorinfor);
		majorinfo.setUnivid(book.getId());


		if(!majorinfoDao.add(majorinfo)){
			ret.put("type", "error");
			ret.put("msg", "图书更新失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "图书更新成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

//	private void addBook(HttpServletRequest request,
//			HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		String name = request.getParameter("name");
//		int universityCategoryId = Integer.parseInt(request.getParameter("universityCategoryId"));
//		String province  = request.getParameter("province");
//		String rank  = request.getParameter("rank");
//		String address  = request.getParameter("address");
////		int status = Integer.parseInt(request.getParameter("status"));
////		int number = Integer.parseInt(request.getParameter("number"));
//		String info = request.getParameter("info");
//		Map<String, Object> ret = new HashMap<String, Object>();
//		if(StringUtil.isEmpty(name)){
//			ret.put("type", "error");
//			ret.put("msg", "高校名称不能为空!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
//		if(StringUtil.isEmpty(province)){
//			ret.put("type", "error");
//			ret.put("msg", "高校省会不能为空!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
//		if(StringUtil.isEmpty(address)){
//			ret.put("type", "error");
//			ret.put("msg", "高校地址不能为空!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
//		if(StringUtil.isEmpty(rank)){
//			rank="无";
//		}
//		UniversityCategory universityCategory = universityCategoryDao.find(universityCategoryId);
//		if(universityCategory == null){
//			ret.put("type", "error");
//			ret.put("msg", "高校分类不能为空!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
//		
//		Book book = new Book();
//		book.setUniversityCategory(universityCategory);
//		book.setName(name);
//		book.setProvince(province);
//		book.setAddress(address);
//		book.setRank(rank);
////		book.setStatus(status);
////		book.setNumber(number);
////		book.setFreeNumber(number);
//		book.setInfo(info);
//		if(!bookDao.add(book)){
//			ret.put("type", "error");
//			ret.put("msg", "图书添加失败，请联系管理员!");
//			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//			return;
//		}
//		ret.put("type", "success");
//		ret.put("msg", "图书添加成功!");
//		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
//	}

	private void getBookList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("univname");
		String bbcid = request.getParameter("majortypeCategoryId");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<MajorInfo> page = new Page<MajorInfo>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("univname", "%"+name+"%", Operator.LIKE));
//		page.getSearchProporties().add(new SearchProperty("majorname", "%"+name+"%", Operator.LIKE));
		if(!StringUtil.isEmpty(bbcid) && !"0".equals(bbcid)){
			page.getSearchProporties().add(new SearchProperty("majortype_category", majortypeCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
		}
		page =majorinfoDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	private boolean isExistProperty(String username,String search,int id){
		Page<Book> page = new Page<Book>(1, 10);
		page.getSearchProporties().add(new SearchProperty(search, username, Operator.EQ));
		page = bookDao.findList(page);
		if(page.getContent().size() > 0){
			Book book = page.getContent().get(0);
			if(book.getId() != id)return true;
		} 
		return false;
	}
	

}