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
import dao.SongDao;
import entity.Song;
import page.Operator;
import page.Page;
import page.SearchProperty;
import util.StringUtil;

public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 5870852067427524781L;
    private  SongDao songdao = new SongDao();

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
    }

    private void SearchSong(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        Map<String, Object> ret = new HashMap<String, Object>();
        List<Song> result= new ArrayList<>();
        response.setCharacterEncoding("UTF-8");
        String key = request.getParameter("key");
        Page<Song> page = new Page<Song>(1, 999);
//        System.out.println(key);
//        key="%"+key+"%";
//        page.getSearchProporties().add(new SearchProperty("song_name", key, Operator.EQ));
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
//        Song song=page.getContent().get(0);
//        System.out.println(song.getAuthor_name());
        result = page.getContent();
//        System.out.println(result);

        ret.put("type", "success");
        ret.put("values",result);
        StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
    }
}
