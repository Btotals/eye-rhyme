package com.controllers.activity;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.MovieActivity;
import com.models.User;
import com.repositories.MovieActivityRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;
import net.sf.json.JSONObject;

@RestController
public class JoinorLeaveActivityController {
    
	@Autowired
	MovieActivityRepository movieActivityRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_activity/operate_activity", method=RequestMethod.GET)
    public String operateActivity() {
        return "this controller is for joining or leaving an activity...";
    }
    
    @RequestMapping(value="movie_activity/operate_activity", method=RequestMethod.POST)
    public String operateActivity(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.ACTIVITY_ID) && jsonObj.containsKey(Keyword.USER_ID) && jsonObj.containsKey(Keyword.TYPE)) {
				MovieActivity movieActivity = movieActivityRepository.findMovieActivityById(jsonObj.getInt(Keyword.ACTIVITY_ID));
				User user = userRepository.findOne(jsonObj.getInt(Keyword.USER_ID));
				if (user != null && movieActivity != null) {
					Integer type = jsonObj.getInt(Keyword.TYPE);
					if (type == Values.ONE) {
						if (!movieActivity.getParticipants().contains(user)) {
							movieActivity.getParticipants().add(user);
							movieActivityRepository.save(movieActivity);
							map.put(Keyword.STATUS, Values.OK);
							return JSONObject.fromObject(map).toString();
						}
					} else {
						if (movieActivity.getParticipants().contains(user)) {
							movieActivity.getParticipants().remove(user);
							movieActivityRepository.save(movieActivity);
							map.put(Keyword.STATUS, Values.OK);
							return JSONObject.fromObject(map).toString();
						}
					}
				}
			}
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }
}

