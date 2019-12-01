package com.ischoolbar.programmer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ischoolbar.programmer.entity.User;
/**
 * ÏµÍ³Ö÷Ò³
 * 
 *
 */
public class SystemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7258264317769166483L;
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
		User loginUser = (User)request.getSession().getAttribute("user");
		String method = "";
		if(attribute != null){
			method = attribute.toString();
		}
		if("index".equals(method)){
			if(loginUser.getType()==1){
				request.getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				return;
			}
			else
			{
				request.getRequestDispatcher("/WEB-INF/views/system.jsp").forward(request, response);
				return;
			}
			
		}
		
		
	}
}
