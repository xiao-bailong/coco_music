package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import dao.Search_historyDao;
import dao.SongDao;
import dao.SonglistDao;
import entity.Search_history;
import entity.Song;
import entity.Songlist;
import page.Operator;
import page.Page;
import page.SearchProperty;
import util.StringUtil;

public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 5870852067427524781L;
    private  SongDao songdao = new SongDao();
    private SonglistDao songlistDao= new SonglistDao();
    private Search_historyDao historyDao = new Search_historyDao();
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
        if("SearchSong".equals(method)){
            SearchSong(request,response);
            return;
        }
        if("GetHistory".equals(method)){
            GetHistory(request,response);
            return;
        }
        if("SearchSongList".equals(method)){
            SearchSongList(request,response);
            return;
        }
        if("DeleteHistory".equals(method)){
            DeleteHistory(request,response);
            return;
        }
    }
    //删除搜索历史
    private void DeleteHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO Auto-generated method stub
        Map<String, String> ret = new HashMap<String, String>();
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        System.out.println(id);
        try{
            int id2 = Integer.valueOf(id).intValue();
            Page<Search_history> page = new Page<Search_history>(1, 999);
            page.getSearchProporties().add(new SearchProperty("id", id2, Operator.EQ));
            page = historyDao.findList(page);
            Search_history his=page.getContent().get(0);
            System.out.println(id2);
            System.out.println(his.getHistory());
            if(!historyDao.delete(id2)){
                ret.put("type", "error");
                ret.put("msg", "搜索历史删除失败，请联系管理员！");
                response.getWriter().write(JSONObject.toJSONString(ret));
                return;
            }
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        response.getWriter().write(JSONObject.toJSONString(ret));
    }
    //获取搜索历史
    private void GetHistory(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Search_history> result= new ArrayList<>();
        response.setCharacterEncoding("UTF-8");
        Page<Search_history> page = new Page<Search_history>(1, 999);
        page = historyDao.findList(page);
        result = page.getContent();
        System.out.println(result.get(0).getHistory());
        ret.put("type", "success");
        ret.put("values",result);
        StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
    }
    //搜索歌曲
    private void SearchSong(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Song> result= new ArrayList<>();
        response.setCharacterEncoding("UTF-8");
        String key = request.getParameter("key");
        //添加搜索历史
        Page<Search_history> page1 = new Page<Search_history>(1, 999);
        page1.getSearchProporties().add(new SearchProperty("history", key, Operator.EQ));
        page1 = historyDao.findList(page1);
        System.out.println(key);
        System.out.println(page1.getContent().size());
        /*if(page1.getContent().size() == 0){
            System.out.println("为什么会进来1");
            Search_history history=new Search_history();
            history.setHistory(key);
            historyDao.add(history);
        }*/
        if(!"".equals(key)){
            System.out.println(233);
        } else{
            System.out.println(777);
        }
        if(!"".equals(key)&&page1.getContent().size() == 0){
            System.out.println(996);
            Search_history history=new Search_history();
            history.setHistory(key);
            historyDao.add(history);
        }

        Page<Song> page = new Page<Song>(1, 999);
        page.getSearchProporties().add(new SearchProperty("song_name", "%"+key+"%", Operator.LIKE));
        page.getSearchProporties().add(new SearchProperty("author_name", "%"+key+"%", Operator.LIKEOR));
        page.getSearchProporties().add(new SearchProperty("album_name", "%"+key+"%", Operator.LIKEOR));
        page = songdao.findList(page);
        if(page.getContent().size() == 0){
            ret.put("type", "error");
            ret.put("msg", "该歌曲信息不存在");
            StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
            return;
        }
        result = page.getContent();
        ret.put("type", "success");
        ret.put("values",result);
        StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
    }
//    搜索歌单
    private void SearchSongList(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Songlist> result= new ArrayList<>();
        response.setCharacterEncoding("UTF-8");
        String key = request.getParameter("key");
        //添加搜索历史
        Page<Search_history> page1 = new Page<Search_history>(1, 999);
        page1.getSearchProporties().add(new SearchProperty("history", key, Operator.EQ));
        page1 = historyDao.findList(page1);
        System.out.println(key);
        System.out.println(page1.getContent().size());
        /*if(page1.getContent().size() == 0){
            System.out.println("为什么会进来2");
            Search_history history=new Search_history();
            history.setHistory(key);
            historyDao.add(history);
        }*/
        if(!"".equals(key)&&page1.getContent().size() == 0){
            System.out.println("为什么会进来2");
            Search_history history=new Search_history();
            history.setHistory(key);
            historyDao.add(history);
        }

        Page<Songlist> page = new Page<Songlist>(1, 999);
        page.getSearchProporties().add(new SearchProperty("songlist_name", "%"+key+"%", Operator.LIKE));
        page = songlistDao.findList(page);
        if(page.getContent().size() == 0){
            ret.put("type", "error");
            ret.put("msg", "该歌单信息不存在");
            StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
            return;
        }
        result = page.getContent();
        ret.put("type", "success");
        ret.put("values",result);
        StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
    }
}
