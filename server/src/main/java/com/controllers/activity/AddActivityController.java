package com.controllers.activity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.MovieActivity;
import com.models.MovieDescription;
import com.models.User;
import com.repositories.MovieActivityRepository;
import com.repositories.MovieDescriptionRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class AddActivityController {
    
	@Autowired
	MovieActivityRepository movieActivityRepository;
	
	@Autowired
	MovieDescriptionRepository movieDescriptionRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_activity/add_activity", method=RequestMethod.GET)
    public String addActivity() {
        return "this controller is for adding a movie activity...";
    }
    
    @RequestMapping(value="movie_activity/add_activity", method=RequestMethod.POST)
    public String addActivity(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.MOVIE_ID) && jsonObj.containsKey(Keyword.USER_ID)
					&& jsonObj.containsKey(Keyword.CONTENT) && jsonObj.containsKey(Keyword.DATE_TIME)
					&& jsonObj.containsKey(Keyword.CONTACT) && jsonObj.containsKey(Keyword.PLACE)
					&& jsonObj.containsKey(Keyword.LONGITUDE) && jsonObj.containsKey(Keyword.LATITUDE)
					&& jsonObj.containsKey(Keyword.JOIN_BOUND)) {
				
				User user = userRepository.findUserById(jsonObj.getInt(Keyword.USER_ID));
				
				MovieDescription movieDescription = movieDescriptionRepository.findMovieDescriptionById(jsonObj.getInt(Keyword.MOVIE_ID));
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date_time = sdf.parse(jsonObj.getString(Keyword.DATE_TIME));
				String place = jsonObj.getString(Keyword.PLACE);
				String content = jsonObj.getString(Keyword.CONTENT);
				String contact = jsonObj.getString(Keyword.CONTACT);
				BigDecimal longitude = new BigDecimal(jsonObj.getString(Keyword.LONGITUDE));
				BigDecimal latitude = new BigDecimal(jsonObj.getString(Keyword.LATITUDE));
				Integer bound = jsonObj.getInt(Keyword.JOIN_BOUND);
				MovieActivity movieActivity = new MovieActivity(date_time, place, content, longitude, latitude, contact, bound);
				movieActivity.setLauncher(user);
				movieActivity.setMovieDescription(movieDescription);
				movieActivityRepository.save(movieActivity);
				
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
