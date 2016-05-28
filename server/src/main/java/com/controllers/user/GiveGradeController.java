package com.controllers.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.repositories.MovieDescriptionRepository;
import com.repositories.TheaterRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import com.models.MovieDescription;
import com.models.MovieGrade;
import com.models.Theater;
import com.models.TheaterGrade;
import com.models.User;

import net.sf.json.JSONObject;


@RestController
public class GiveGradeController {
    
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TheaterRepository theaterRepository;
	
	@Autowired
	MovieDescriptionRepository movieDescriptionRepository;
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @RequestMapping(value="user/give_grades", method=RequestMethod.GET)
    public String giveGrade() {
        return "this controller is for giving grades towards a specific theater...";
    }
    
    @RequestMapping(value="user/give_grades", method=RequestMethod.POST)
    public String giveGrade(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.USER_ID) 
    				&& jsonObj.containsKey(Keyword.TYPE) 
    				&& jsonObj.containsKey(Keyword.OBJECT_ID) 
    				&& jsonObj.containsKey(Keyword.VALUE) 
    				&& jsonObj.containsKey(Keyword.CONTENT)) {
    			
    			User user = userRepository.findUserById(jsonObj.getInt(Keyword.USER_ID));
    			Integer value = jsonObj.getInt(Keyword.VALUE);
    			String content = jsonObj.getString(Keyword.CONTENT);
    			if (jsonObj.getInt(Keyword.TYPE) == Values.ZERO) {
    				Theater theater = theaterRepository.findTheaterById(jsonObj.getInt(Keyword.OBJECT_ID));
    				TheaterGrade theaterGrade = new TheaterGrade(user, value, content);
    				theater.getGrades().add(theaterGrade);
    				theaterRepository.save(theater);
    			} else {
    				MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(jsonObj.getInt(Keyword.OBJECT_ID));
    				MovieGrade movieGrade= new MovieGrade(user, value, content);
    				movieDescription.getGrades().add(movieGrade);
    				movieDescriptionRepository.save(movieDescription);
    			}
   
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
