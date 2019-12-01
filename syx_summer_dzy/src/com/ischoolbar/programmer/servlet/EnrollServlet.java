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
import com.ischoolbar.programmer.dao.EnrollDao;
import com.ischoolbar.programmer.dao.MajorInfoDao;
import com.ischoolbar.programmer.dao.SubjectCategoryDao;
import com.ischoolbar.programmer.dao.UserInfoDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.Enroll;
import com.ischoolbar.programmer.entity.MajorInfo;
import com.ischoolbar.programmer.entity.MajortypeCategory;
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
public class EnrollServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5036792054338741106L;
	/**
	 * 
	 */

	private BookDao bookDao = new BookDao();
	private MajorInfoDao majorinfoDao = new MajorInfoDao();
	private EnrollDao enrollDao = new EnrollDao();
	private UserInfoDao userinfoDao = new UserInfoDao();
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
		if("toEnrollListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/enroll.jsp").forward(request, response);
			return;
		}
		if("EnrollList".equals(method)){
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
		if(!enrollDao.delete(idArr)){
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
		String univname = request.getParameter("univname");
		String majorname = request.getParameter("majorname");
		int id = Integer.parseInt(request.getParameter("id"));
		int userid = Integer.parseInt(request.getParameter("userid"));
		Map<String, Object> ret = new HashMap<String, Object>();
		
		if(StringUtil.isEmpty(univname)){
			ret.put("type", "error");
			ret.put("msg", "分类名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(univname)){
			ret.put("type", "error");
			ret.put("msg", "分类名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		UserInfo userinfo = userinfoDao.find(userid,"userid");
		Enroll enroll = new Enroll();
		enroll.setId(id);
		enroll.setUnivname(univname);
		enroll.setMajorname(majorname);
		enroll.setUserid(userid);
		enroll.setName(userinfo.getUsername());
		enroll.setIdnum(userinfo.getIdnum());
		if(!enrollDao.update(enroll)){
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
		String univname = request.getParameter("univname");
		String majorname = request.getParameter("majorname");
		Map<String, Object> ret = new HashMap<String, Object>();
		User loginedUser = (User)request.getSession().getAttribute("user");
		UserInfo userinfo = userinfoDao.find(loginedUser.getId(),"userid");
		if(userinfo==null)
		{
			ret.put("type", "error");
			ret.put("msg", "请先完善您的用户信息!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(univname)){
			ret.put("type", "error");
			ret.put("msg", "分类名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(!isExistProperty(univname,"univname",0)){
			ret.put("type", "error");
			ret.put("msg", "该高校名不存在，请仔细查阅高校名称!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(majorname)){
			ret.put("type", "error");
			ret.put("msg", "分类名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(!isExistProperty(majorname,"majorname",0)){
			ret.put("type", "error");
			ret.put("msg", "该高校无此专业，请仔细该高校专业名称!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
//		UserInfo userinfo = 1;
		Enroll enroll1 = enrollDao.find(univname,majorname,"univname","majorname");
		if(enroll1!=null)
		{
			ret.put("type", "error");
			ret.put("msg", "您已经申请过该专业!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		MajorInfo majorinfo = majorinfoDao.find(univname,majorname,"univname","majorname");
		User loginUser = (User)request.getSession().getAttribute("user");
		int userid = loginUser.getId();
		if(userinfo.getTotal_a()<majorinfo.getAnum()){
			ret.put("type", "error");
			ret.put("msg", "您学考A个数没有达到该专业要求!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		String subject1 = majorinfo.getResubjects();
		String subject2 = userinfo.getSubj1().getName();
//		System.out.print(subject1.contains(subject2));
		if(!(majorinfo.getResubjects().contains(userinfo.getSubj1().getName())||majorinfo.getResubjects().contains(userinfo.getSubj2().getName())||majorinfo.getResubjects().contains(userinfo.getSubj3().getName()))&&!majorinfo.getResubjects().equals("不限")){
			ret.put("type", "error");
			ret.put("msg", "您选考科目没有达到该专业要求!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		Enroll enroll = new Enroll();
		enroll.setUnivname(univname);
		enroll.setMajorname(majorname);
		enroll.setUserid(userid);
		enroll.setName(userinfo.getUsername());
		enroll.setIdnum(userinfo.getIdnum());
		if(!enrollDao.add(enroll)){
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
		Page<Enroll> page = new Page<Enroll>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("name", "%"+name+"%", Operator.LIKE));
		User loginedUser = (User)request.getSession().getAttribute("user");
		if(loginedUser.getType() == 2){
			//他是普通用户
			page.getSearchProporties().add(new SearchProperty("userid", loginedUser.getId(), Operator.EQ));
		}
		page = enrollDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	private boolean isExistProperty(String username,String search,int id){
		Page<MajorInfo> page = new Page<MajorInfo>(1, 10);
		page.getSearchProporties().add(new SearchProperty(search, username, Operator.EQ));
		page = majorinfoDao.findList(page);
		if(page.getContent().size() > 0){
			MajorInfo majorinfo = page.getContent().get(0);
			if(majorinfo.getId() != id)return true;
		} 
		return false;
	}

	
}
