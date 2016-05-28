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
public class UpdateArticleController {
    
	@Autowired
	MovieArticleRepository movieArticleRepository;
	
    @RequestMapping(value="movie_article/update_article", method=RequestMethod.GET)
    public String updateArticle() {
        return "this controller is for update article's attributes...";
    }
    
    @RequestMapping(value="movie_article/update_article", method=RequestMethod.POST)
    public String updateArticle(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.ARTICLE_ID) && jsonObj.containsKey(Keyword.TYPE)) {
				MovieArticle movieArticle = movieArticleRepository.findMovieArticleById(jsonObj.getInt(Keyword.ARTICLE_ID));
			    Integer type = jsonObj.getInt(Keyword.TYPE);
			    if (type == 0) movieArticle.setReadNum(movieArticle.getReadNum() + 1);
				if (type == 1) movieArticle.setLikeNum(movieArticle.getLikeNum() + 1);
				
				movieArticleRepository.save(movieArticle);
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
