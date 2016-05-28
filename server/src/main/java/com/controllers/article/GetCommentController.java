package com.controllers.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modelboosters.Comment;
import com.models.User;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class GetCommentController {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_article/get_comments", method=RequestMethod.GET)
    public String getMovies() {
        return "this controller is for getting movie articles' comments...";
    }
    
    @RequestMapping(value="movie_article/get_comments", method=RequestMethod.POST)
    public String getMovies(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			// following is the SQL's definitions here..
			String SQL = String.format("select comment_id, user_id, date_time, content, like_num from comment where movie_article_id = %d", jsonObj.getInt(Keyword.ARTICLE_ID));
			if (jsonObj.containsKey(Keyword.COMMENT_ID)) SQL += String.format(" and comment_id < %d", jsonObj.getInt(Keyword.COMMENT_ID));
			SQL += " order by comment_id DESC";
			SQL += String.format(" limit %d", Values.LIMIT);
			
			List<Comment> comments = jdbcTemplate.query(SQL, (rs, rowNum) -> new Comment(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
			
			// List<String> list = new ArrayList<String>();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (Comment comment: comments) {
				Map<String, String> tmap = new HashMap<String, String>();
				tmap.put(Keyword.COMMENT_ID, comment.getComment_id().toString());
		        tmap.put(Keyword.USER_ID, comment.getUser_id().toString());
		        User user = userRepository.findUserById(comment.getUser_id());
		        tmap.put(Keyword.RANK, user.getRank().toString());
			    tmap.put(Keyword.GENDER, user.getGender().toString());
			    tmap.put(Keyword.NAME, user.getName());
		        
		        tmap.put(Keyword.CONTENT, comment.getContent());
		        tmap.put(Keyword.LIKE_NUM, comment.getLike_num().toString());
		        tmap.put(Keyword.DATE_TIME, comment.getDate_time());
		        
				list.add(tmap);
			}
			
			map.put(Keyword.STATUS, Values.OK);
			map.put(Keyword.RESULT, list);
    		return JSONObject.fromObject(map).toString();
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }

}
