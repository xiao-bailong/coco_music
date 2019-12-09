package servlet;

import  com.alibaba.fastjson.JSONObject;
import dao.UserDao;
import entity.User;
import page.Operator;
import page.Page;
import page.SearchProperty;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class UserInforServlet extends HttpServlet{
    private static final long serialVersionUID = -5870852067427524781L;

    private UserDao userDao = new UserDao();

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
        if("changePwd".equals(method)){
            changePwd(request,response);
            return;
        }
        if("changeInfor".equals(method)){
            changeInfor(request,response);
            return;
        }
        if("getAllInf".equals(method)){
            getAllInf(request,response);
            return;
        }
    }

    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        String user_id = request.getParameter("user_id");
        String password = request.getParameter("password");
        String newpassword = request.getParameter("newpassword");
        Page<User> page = new Page<User>(1, 10);
        page.getSearchProporties().add(new SearchProperty("user_id", user_id, Operator.EQ));
        page = userDao.findList(page);
        System.out.println(page.getTotal());
        User user = page.getContent().get(0);
        if(!password.equals(user.getPassword())){
            ret.put("type", "error");
            ret.put("msg", "原密码错误！");
            response.getWriter().write(JSONObject.toJSONString(ret));
            return;
        }
        user.setId(user.getId());
        user.setPassword(newpassword);
        if(!userDao.update(user)){
            ret.put("type", "error");
            ret.put("msg", "密码修改失败，请联系管理员！");
            response.getWriter().write(JSONObject.toJSONString(ret));
            return;
        }
        ret.put("type", "success");
        response.getWriter().write(JSONObject.toJSONString(ret));
    }

    private void changeInfor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        response.setCharacterEncoding("UTF-8");
        String user_id = request.getParameter("user_id");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String introduction = request.getParameter("introduction");
        if(isExistUserName(name)){
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在，请重新输入！");
            response.getWriter().write(JSONObject.toJSONString(ret));
            return;
        }
        Page<User> page = new Page<User>(1, 10);
        page.getSearchProporties().add(new SearchProperty("user_id", user_id, Operator.EQ));
        page = userDao.findList(page);
        User user = page.getContent().get(0);
        user.setId(user.getId());
        user.setName(name);
        user.setSex(sex);
        user.setIntroduction(introduction);
        try {
            java.sql.Date sDate= new java.sql.Date((sdf.parse(birthday)).getTime());
            user.setBirthday(sDate);
            /*java.util.Date uDate= new java.util.Date((sdf.parse(birthday)).getTime());
            user.setBirthday2(uDate);*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(!userDao.update(user)){
            ret.put("type", "error");
            ret.put("msg", "个人信息修改失败，请联系管理员！");
            response.getWriter().write(JSONObject.toJSONString(ret));
            return;
        }
        ret.put("type", "success");
        response.getWriter().write(JSONObject.toJSONString(ret));
    }

    private void getAllInf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        String user_id = request.getParameter("user_id");
        Page<User> page = new Page<User>(1, 10);
        page.getSearchProporties().add(new SearchProperty("user_id", user_id, Operator.EQ));
        page = userDao.findList(page);
        User user = page.getContent().get(0);
        ret.put("user", user);
//        ret.put("birthday",user.getBirthday());
//        ret.put("birthday2",user.getBirthday2());
//        System.out.println(user.getBirthday());
//        response.getWriter().write(JSONObject.toJSONString(ret));
        StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
    }
    //查询这个用户名是否已存在
    private boolean isExistUserName(String name){
        Page<User> page = new Page<User>(1, 10);
        page.getSearchProporties().add(new SearchProperty("name", name, Operator.EQ));
        page = userDao.findList(page);
        if(page.getContent().size() > 0)return true;
        return false;
    }
}
