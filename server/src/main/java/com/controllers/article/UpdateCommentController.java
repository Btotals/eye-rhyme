package com.controllers.article;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class UpdateCommentController {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @RequestMapping(value="movie_article/update_article_comment", method=RequestMethod.GET)
    public String updateComments() {
        return "this controller is for update article's comments' attributes...";
    }
    
    @RequestMapping(value="movie_article/update_article_comment", method=RequestMethod.POST)
    public String updateComments(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.ARTICLE_ID) && jsonObj.containsKey(Keyword.COMMENT_ID)) {
				Integer article_id = jsonObj.getInt(Keyword.ARTICLE_ID);
				Integer comment_id = jsonObj.getInt(Keyword.COMMENT_ID);
				String SQL = String.format("update comment set like_num = like_num + 1 where movie_article_id = %d and comment_id = %d", article_id, comment_id);
				jdbcTemplate.update(SQL);
				
				map.put(Keyword.STATUS, Values.OK);
				return JSONObject.fromObject(map).toString();
			}
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }
}
