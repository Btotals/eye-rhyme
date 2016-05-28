package com.controllers.article;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.MovieArticle;
import com.models.MovieDescription;
import com.models.User;
import com.repositories.MovieArticleRepository;
import com.repositories.MovieDescriptionRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class AddArticleController {
    
	@Autowired
	MovieArticleRepository movieArticleRepository;
	
	@Autowired
	MovieDescriptionRepository movieDescriptionRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_article/add_article", method=RequestMethod.GET)
    public String addArticle() {
        return "this controller is for adding an article...";
    }
    
    @RequestMapping(value="movie_article/add_article", method=RequestMethod.POST)
    public String addArticle(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.MOVIE_ID) && jsonObj.containsKey(Keyword.USER_ID)
					&& jsonObj.containsKey(Keyword.CONTENT) && jsonObj.containsKey(Keyword.TITLE)) {
				User user = userRepository.findUserById(jsonObj.getInt(Keyword.USER_ID));
				MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(jsonObj.getInt(Keyword.MOVIE_ID));
				String title = jsonObj.getString(Keyword.TITLE);
				String content = jsonObj.getString(Keyword.CONTENT);
				movieArticleRepository.save(new MovieArticle(user, movieDescription, title, content, Values.EMPTY));
				
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
