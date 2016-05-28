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

import com.models.MovieArticle;
import com.repositories.MovieArticleRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class GetArticleController {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	MovieArticleRepository movieArticleRepository;
	
    @RequestMapping(value="movie_article/get_articles", method=RequestMethod.GET)
    public String getMovies() {
        return "this controller is for getting movie articles...";
    }
    
    @RequestMapping(value="movie_article/get_articles", method=RequestMethod.POST)
    public String getMovies(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			// following is the SQL's definitions here..
			String SQL = String.format("select id from movie_article");
			if (jsonObj.containsKey(Keyword.ARTICLE_ID)) SQL += String.format(" where id < %d", jsonObj.getInt(Keyword.ARTICLE_ID));
			SQL += " order by id DESC";
			SQL += String.format(" limit %d", Values.LIMIT);
			List<Integer> articleIds = jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1)));
			
			// prepare the information about every movie..
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (Integer id: articleIds) {
				MovieArticle movieArticle = movieArticleRepository.findMovieArticleById(id);
			    Map<String, String> tmap = new HashMap<String, String>();
		        tmap.put(Keyword.ARTICLE_ID, id.toString());
		        tmap.put(Keyword.MOVIE_ID, movieArticle.getMovieDescription().getId().toString());
		        tmap.put(Keyword.MOVIE_NAME, movieArticle.getMovieDescription().getName());
		        tmap.put(Keyword.AUTHOR, movieArticle.getAuthor().getName());
		        tmap.put(Keyword.TITLE, movieArticle.getTitle());
		        tmap.put(Keyword.READ_NUM, movieArticle.getReadNum().toString());
		        tmap.put(Keyword.LIKE_NUM, movieArticle.getLikeNum().toString());
		        tmap.put(Keyword.COMMENT_NUM, getCommentNum(id).toString());
		        
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
    
    public Integer getCommentNum(Integer id) {
    	String SQL = String.format("select COUNT(user_id) from comment where movie_article_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1))).get(0);
    }
}
