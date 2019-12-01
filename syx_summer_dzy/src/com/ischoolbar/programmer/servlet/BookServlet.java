package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ischoolbar.programmer.dao.MajorInfoDao;
import com.ischoolbar.programmer.dao.UniversityCategoryDao;
import com.ischoolbar.programmer.dao.BookDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.MajorInfo;
import com.ischoolbar.programmer.entity.UniversityCategory;
import com.ischoolbar.programmer.entity.UserInfo;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * ͼ����������
 * 
 *
 */
public class BookServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4386109520796986005L;

	private BookDao bookDao = new BookDao();
	private MajorInfoDao majorinfoDao = new MajorInfoDao();
	
	private UniversityCategoryDao universityCategoryDao = new UniversityCategoryDao();
	
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
		if("toBookListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/book.jsp").forward(request, response);
			return;
		}
		if("BookList".equals(method)){
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
		Page<UniversityCategory> page = new Page<UniversityCategory>(1, 999);
		page = universityCategoryDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		UniversityCategory bookCategory = new UniversityCategory();
		bookCategory.setId(0);
		bookCategory.setName("ȫ��");
		page.getContent().add(bookCategory);
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
		for(int i =0;i<ids.length;i++)
		{
			Book book = bookDao.find(ids[i],"id");
			MajorInfo majorinfo =majorinfoDao.find(book.getName(),"univname");
			if(majorinfo!=null)
			{
				ret.put("type", "error");
				ret.put("msg", "��У "+book.getName()+" ����רҵ������Ϣ���޷�ɾ����!");
				StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
				return;
			}
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!bookDao.delete(idArr)){
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
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		int bookCategoryId = Integer.parseInt(request.getParameter("universityCategoryId"));
		String province  = request.getParameter("province");
		String rank  = request.getParameter("rank");
		String address  = request.getParameter("address");
//		String province  = request.getParameter("");
		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "ͼ�����Ʋ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		UniversityCategory universityCategory = universityCategoryDao.find(bookCategoryId);
		if(universityCategory == null){
			ret.put("type", "error");
			ret.put("msg", "ͼ����಻��Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		//���ͼ�������Ƿ���ڽ������
		Book oldBook = bookDao.find(id);
		//�Ѿ����������
//		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
//		if(number < borrowedNumber){
//			ret.put("type", "error");
//			ret.put("msg", "��������С���Ѿ����������!");
//			StringUtil.writrToPage(response,JSONObject.toJSONString(ret));
//			return;
//		}
		
		Book book = new Book();
		book.setId(id);
		book.setUniversityCategory(universityCategory);
		book.setName(name);
		book.setProvince(province);
		book.setInfo(info);
		book.setAddress(address);
		book.setRank(rank);
		
		if(!bookDao.update(book)){
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
		String name = request.getParameter("name");
		int universityCategoryId = Integer.parseInt(request.getParameter("universityCategoryId"));
		String province  = request.getParameter("province");
		String rank  = request.getParameter("rank");
		String address  = request.getParameter("address");
//		int status = Integer.parseInt(request.getParameter("status"));
//		int number = Integer.parseInt(request.getParameter("number"));
		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "��У���Ʋ���Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(province)){
			ret.put("type", "error");
			ret.put("msg", "��Уʡ�᲻��Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(address)){
			ret.put("type", "error");
			ret.put("msg", "��У��ַ����Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		if(StringUtil.isEmpty(rank)){
			rank="��";
		}
		UniversityCategory universityCategory = universityCategoryDao.find(universityCategoryId);
		if(universityCategory == null){
			ret.put("type", "error");
			ret.put("msg", "��У���಻��Ϊ��!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		
		Book book = new Book();
		book.setUniversityCategory(universityCategory);
		book.setName(name);
		book.setProvince(province);
		book.setAddress(address);
		book.setRank(rank);
//		book.setStatus(status);
//		book.setNumber(number);
//		book.setFreeNumber(number);
		book.setInfo(info);
		if(!bookDao.add(book)){
			ret.put("type", "error");
			ret.put("msg", "ͼ�����ʧ�ܣ�����ϵ����Ա!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "ͼ����ӳɹ�!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	private void getBookList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String bbcid = request.getParameter("universityCategoryId");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<Book> page = new Page<Book>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("name", "%"+name+"%", Operator.LIKE));
		if(!StringUtil.isEmpty(bbcid) && !"0".equals(bbcid)){
			page.getSearchProporties().add(new SearchProperty("university_category", universityCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
		}
		page = bookDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	
}
