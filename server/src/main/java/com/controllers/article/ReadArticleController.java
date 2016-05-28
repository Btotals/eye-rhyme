package com.controllers.article;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ReadArticleController {
    
	@Autowired
	MovieArticleRepository movieArticleRepository;
	
    @RequestMapping(value="movie_article/read_article", method=RequestMethod.GET)
    public String getMovies() {
        return "this controller is for getting article's content information...";
    }
    
    @RequestMapping(value="movie_article/read_article", method=RequestMethod.POST)
    public String getMovies(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.ARTICLE_ID)) {
				MovieArticle movieArticle = movieArticleRepository.findMovieArticleById(jsonObj.getInt(Keyword.ARTICLE_ID));
				map.put(Keyword.STATUS, Values.OK);
				map.put(Keyword.CONTENT, movieArticle.getContent());
		        map.put(Keyword.DATE_TIME, movieArticle.getDate().toString());
		        map.put(Keyword.USER_ID, movieArticle.getAuthor().getId().toString());
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
