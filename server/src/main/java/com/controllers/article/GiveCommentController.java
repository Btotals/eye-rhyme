package com.controllers.article;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.Comment;
import com.models.MovieArticle;
import com.models.User;
import com.repositories.MovieArticleRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class GiveCommentController {
    
	@Autowired
	MovieArticleRepository movieArticleRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_article/give_comment", method=RequestMethod.GET)
    public String addComments() {
        return "this controller is for adding an comment...";
    }
    
    @RequestMapping(value="movie_article/give_comment", method=RequestMethod.POST)
    public String addComments(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.ARTICLE_ID) && jsonObj.containsKey(Keyword.USER_ID)
					&& jsonObj.containsKey(Keyword.CONTENT)) {
				MovieArticle movieArticle = movieArticleRepository.findMovieArticleById(jsonObj.getInt(Keyword.ARTICLE_ID));
				User user = userRepository.findUserById(jsonObj.getInt(Keyword.USER_ID));
				movieArticle.getComments().add(new Comment(user, jsonObj.getString(Keyword.CONTENT)));
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
