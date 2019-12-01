package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.ischoolbar.programmer.dao.RecommandDao;
import com.ischoolbar.programmer.dao.SubjectCategoryDao;
import com.ischoolbar.programmer.dao.UserInfoDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.MajorInfo;
import com.ischoolbar.programmer.entity.MajortypeCategory;
import com.ischoolbar.programmer.entity.Recommand;
import com.ischoolbar.programmer.entity.SubjectCategory;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.entity.UserInfo;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * 高校分类管理控制器
 * 
 *
 */
public class RecommandServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5036792054338741106L;
	/**
	 * 
	 */

	private SubjectCategoryDao subjectCategoryDao = new SubjectCategoryDao();
	private BookDao bookDao = new BookDao();
	private MajorInfoDao majorinfoDao = new MajorInfoDao();
	private MajortypeCategoryDao majortypeCategoryDao = new MajortypeCategoryDao();
	private UserInfoDao userinfoDao = new UserInfoDao();
	private RecommandDao recommandDao = new RecommandDao();
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
		if("toRecommandListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/recommand.jsp").forward(request, response);
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
		if("addProperty".equals(method)){
			addProperty(request,response);
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
		String name = request.getParameter("univname");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<Recommand> page = new Page<Recommand>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("univname", "%"+name+"%", Operator.LIKE));
		page = recommandDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}	
	private void addProperty(HttpServletRequest request,
			HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String sql = "truncate recommand";
		PreparedStatement prepareStatement;
		try {
			prepareStatement = majorinfoDao.getDbUtil().connection.prepareStatement(sql);
			ResultSet executeQuery = prepareStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		String bbcid = request.getParameter("majortypeCategoryId");
		User loginUser = (User)request.getSession().getAttribute("user");
		int userid = loginUser.getId();
		UserInfo userinfo = userinfoDao.find(userid,"userid");
		if(userinfo==null)
		{
			ret.put("type", "error");
			ret.put("msg", "请先完善您的用户信息!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int a1 = userinfo.getTotal_a();
		int b1 = userinfo.getTotal_b();
		int c1 = userinfo.getTotal_c();
		int d1 = userinfo.getTotal_d();
		int score = (int) ((a1*10+b1*9+c1*8+d1*7)*3.75+Double.valueOf(userinfo.getTotal_score())/2);
		Page<MajorInfo> page = new Page<MajorInfo>(1, 10);
		page.getSearchProporties().add(new SearchProperty("anum",userinfo.getTotal_a(), Operator.LTE));
		page.getSearchProporties().add(new SearchProperty("majortype_category", majortypeCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
		page.getSearchProporties().add(new SearchProperty("resubjects","%"+userinfo.getSubj1().getName()+"%", Operator.LIKE));
		page.getSearchProporties().add(new SearchProperty("resubjects","%"+userinfo.getSubj2().getName()+"%", Operator.LIKEOR));
		page.getSearchProporties().add(new SearchProperty("resubjects","%"+userinfo.getSubj3().getName()+"%", Operator.LIKEOR));
		
//		if(!StringUtil.isEmpty(bbcid) && !"0".equals(bbcid)){
//			page.getSearchProporties().add(new SearchProperty("majortype_category", majortypeCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
//		}
		page = majorinfoDao.findList(page);
		int i;
		Book book = new Book();
	
		for(i =0;i<page.getContent().size();i++){
			Recommand recommand = new Recommand(); 
			MajorInfo majorinfo = page.getContent().get(i);
			recommand.setMajorname(majorinfo.getMajorname());
			recommand.setUnivname(majorinfo.getUnivname());
			book = bookDao.find(majorinfo.getUnivname(),"name");
			recommand.setRate((int)((score*100/majorinfo.getCyscore()-70)*5+-0.05*Integer.parseInt(book.getRank())));
			if(!recommandDao.add(recommand)){
				ret.put("type", "error");
				ret.put("msg", "分类添加失败，请联系管理员!");
				StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
				return;
			}	
			
		} 
		ret.put("type", "success");
		ret.put("msg", "分类添加成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	
}
