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
import com.ischoolbar.programmer.dao.SubjectCategoryDao;
import com.ischoolbar.programmer.dao.UniversityCategoryDao;
import com.ischoolbar.programmer.dao.UserInfoDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.SubjectCategory;
import com.ischoolbar.programmer.entity.UniversityCategory;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.entity.UserInfo;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * �û����������
 * 
 *
 */
public class UserInfoServlet extends HttpServlet {
	
	private UserInfoDao userinfoDao  = new UserInfoDao();
	private SubjectCategoryDao subjectCategoryDao = new SubjectCategoryDao();
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
		if("toUserInfoListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/user_info.jsp").forward(request, response);
			return;
		}
		if("UserInfoList".equals(method)){
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
		Page<SubjectCategory> page = new Page<SubjectCategory>(1, 999);
		page = subjectCategoryDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		SubjectCategory subjectCategory = new SubjectCategory();
		subjectCategory.setId(0);
		subjectCategory.setName("ȫ��");
		page.getContent().add(subjectCategory);
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
			ret.put("msg", "��ѡ��Ҫɾ��������!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!userinfoDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "ɾ��ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ɾ���ɹ�!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void editBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
	
		String username = request.getParameter("username");
		int id = Integer.parseInt(request.getParameter("id"));
		int userid = Integer.parseInt(request.getParameter("userid"));
		String ID_num = request.getParameter("idnum");
		int subj1Id = Integer.parseInt(request.getParameter("subj1Id"));
		int subj2Id = Integer.parseInt(request.getParameter("subj2Id"));
		int subj3Id = Integer.parseInt(request.getParameter("subj3Id"));
		int total_a = Integer.parseInt(request.getParameter("total_a"));
		int total_b = Integer.parseInt(request.getParameter("total_b"));
		int total_c = Integer.parseInt(request.getParameter("total_c"));
		int total_d = Integer.parseInt(request.getParameter("total_d"));
		String total_score = request.getParameter("total_score");
		String specialty = request.getParameter("specialty");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "ͼ�����Ʋ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		SubjectCategory subj1 = subjectCategoryDao.find(subj1Id);
		SubjectCategory subj2 = subjectCategoryDao.find(subj2Id);
		SubjectCategory subj3 = subjectCategoryDao.find(subj3Id);
		if(subj1 == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀһ����Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(subj2 == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀ������Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(subj3 == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀ������Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_score == null){
			ret.put("type", "error");
			ret.put("msg", "ģ���ֲܷ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(specialty == null){
			ret.put("type", "error");
			ret.put("msg", "�س�����Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(subj1.getName().equals(subj2.getName()) ||subj1.getName().equals(subj3.getName())||subj2.getName().equals(subj3.getName())){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀ������ͬ!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_a<0 ||total_a>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��A�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_b<0 ||total_b>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��B�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_c<0 ||total_c>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��C�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_d<0 ||total_d>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��D�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_a+total_b+total_c+total_d!=10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ���ȵڸ����ܺ��Ƿ�Ϊ10!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(Integer.parseInt(total_score)<0 ||Integer.parseInt(total_score)>750){
			ret.put("type", "error");
			ret.put("msg", "����ģ���ܷ��Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		//���ͼ�������Ƿ���ڽ������
//		Book oldBook = bookDao.find(id);
		//�Ѿ����������
//		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
//		if(number < borrowedNumber){
//			ret.put("type", "error");
//			ret.put("msg", "��������С���Ѿ����������!");
//			StringUtil.writrToPage(response,JSONObject.toJSONString(ret));
//			return;
//		}
		
		UserInfo userinfo = new UserInfo();
		userinfo.setId(id);
		userinfo.setUsername(username);
		userinfo.setSubj1(subj1);
		userinfo.setSubj2(subj2);
		userinfo.setSubj3(subj3);
		userinfo.setIdnum(ID_num);
		userinfo.setTotal_a(total_a);
		userinfo.setTotal_b(total_b);
		userinfo.setTotal_c(total_c);
		userinfo.setTotal_d(total_d);
		userinfo.setTotal_score(total_score);
		userinfo.setSpecialty(specialty);
		userinfo.setUserid(userid);
		if(!userinfoDao.update(userinfo)){
			ret.put("type", "error");
			ret.put("msg", "ͼ�����ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ͼ����³ɹ�!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String ID_num = request.getParameter("idnum");
		int subj1Id = Integer.parseInt(request.getParameter("subj1Id"));
		int subj2Id = Integer.parseInt(request.getParameter("subj2Id"));
		int subj3Id = Integer.parseInt(request.getParameter("subj3Id"));
		int total_a = Integer.parseInt(request.getParameter("total_a"));
		int total_b = Integer.parseInt(request.getParameter("total_b"));
		int total_c = Integer.parseInt(request.getParameter("total_c"));
		int total_d = Integer.parseInt(request.getParameter("total_d"));
		String total_score = request.getParameter("total_score");
		String specialty = request.getParameter("specialty");
//		String province  = request.getParameter("province");
//		String rank  = request.getParameter("rank");
//		String address  = request.getParameter("address");
//		String province  = request.getParameter("");
//		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		User loginUser = (User)request.getSession().getAttribute("user");
		if(isExistUser(loginUser.getId(), 0)){
			ret.put("type", "error");
			ret.put("msg", "���Ѵ����û���Ϣ,�޷����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "ͼ�����Ʋ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		SubjectCategory subj1 = subjectCategoryDao.find(subj1Id);
		SubjectCategory subj2 = subjectCategoryDao.find(subj2Id);
		SubjectCategory subj3 = subjectCategoryDao.find(subj3Id);
		if(subj1 == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀһ����Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(subj2 == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀ������Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(subj3 == null){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀ������Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_score == null){
			ret.put("type", "error");
			ret.put("msg", "ģ���ֲܷ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(specialty == null){
			ret.put("type", "error");
			ret.put("msg", "�س�����Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(subj1.getName().equals(subj2.getName()) ||subj1.getName().equals(subj3.getName())||subj2.getName().equals(subj3.getName())){
			ret.put("type", "error");
			ret.put("msg", "ѡ����Ŀ������ͬ!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_a<0 ||total_a>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��A�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_b<0 ||total_b>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��B�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_c<0 ||total_c>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��C�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(total_d<0 ||total_d>10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ��D�����Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		
		if(total_a+total_b+total_c+total_d!=10){
			ret.put("type", "error");
			ret.put("msg", "����ѧ���ȵڸ����ܺ��Ƿ�Ϊ10!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(Integer.parseInt(total_score)<0 ||Integer.parseInt(total_score)>750){
			ret.put("type", "error");
			ret.put("msg", "����ģ���ܷ��Ƿ�����!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}

		//���ͼ�������Ƿ���ڽ������
//		Book oldBook = bookDao.find(id);
		//�Ѿ����������
//		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
//		if(number < borrowedNumber){
//			ret.put("type", "error");
//			ret.put("msg", "��������С���Ѿ����������!");
//			StringUtil.writrToPage(response,JSONObject.toJSONString(ret));
//			return;
//		}

		UserInfo userinfo = new UserInfo();
		userinfo.setUsername(username);
		userinfo.setSubj1(subj1);
		userinfo.setSubj2(subj2);
		userinfo.setSubj3(subj3);
		userinfo.setIdnum(ID_num);
		userinfo.setTotal_a(total_a);
		userinfo.setTotal_b(total_b);
		userinfo.setTotal_c(total_c);
		userinfo.setTotal_d(total_d);
		userinfo.setTotal_score(total_score);
		userinfo.setSpecialty(specialty);
		userinfo.setUserid(loginUser.getId());
		if(!userinfoDao.add(userinfo)){
			ret.put("type", "error");
			ret.put("msg", "ͼ�����ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ͼ����³ɹ�!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void getBookList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("username");
		String idnum = request.getParameter("idnum");
//		String bbcid = request.getParameter("subj1Id");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<UserInfo> page = new Page<UserInfo>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("username", "%"+name+"%", Operator.LIKE));
		//�жϵ�ǰ�û��Ƿ��ǹ���Ա����������ֻ�ܲ鿴���Լ�����Ϣ
		User loginedUser = (User)request.getSession().getAttribute("user");
		if(loginedUser.getType() == 2){
			//������ͨ�û�
			page.getSearchProporties().add(new SearchProperty("userid", loginedUser.getId(), Operator.EQ));
		}
		if(!StringUtil.isEmpty(idnum)){
			page.getSearchProporties().add(new SearchProperty("idnum", "%"+idnum+"%", Operator.LIKE));

		}
//		if(!StringUtil.isEmpty(bbcid) && !"0".equals(bbcid)){
//			page.getSearchProporties().add(new SearchProperty("subject_category", subjectCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
//		}
		page =userinfoDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	
	private boolean isExistUser(int username,int id){
		Page<UserInfo> page = new Page<UserInfo>(1, 10);
		page.getSearchProporties().add(new SearchProperty("userid", username, Operator.EQ));
		page = userinfoDao.findList(page);
		if(page.getContent().size() > 0){
			UserInfo userinfo = page.getContent().get(0);
			if(userinfo.getId() != id)return true;
		}
		return false;
	}
	

}