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
import com.repositories.MovieActivityRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class UpdateActivityController {
    
	@Autowired
	MovieActivityRepository movieActivityRepository;
	
    @RequestMapping(value="movie_activity/update_activity", method=RequestMethod.GET)
    public String updateActivity() {
        return "this controller is for update activity's attributes...";
    }
    
    @RequestMapping(value="movie_activity/update_activity", method=RequestMethod.POST)
    public String updateActivity(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
			if (jsonObj.containsKey(Keyword.ACTIVITY_ID) && jsonObj.containsKey(Keyword.USER_ID)) {
				MovieActivity movieActivity = movieActivityRepository.findMovieActivityById(jsonObj.getInt(Keyword.ACTIVITY_ID));
				if (movieActivity.getLauncher().getId() == jsonObj.getInt(Keyword.USER_ID)) {
					if (jsonObj.containsKey(Keyword.DATE_TIME)) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date_time = sdf.parse(jsonObj.getString(Keyword.DATE_TIME));
						movieActivity.setDateTime(date_time);
					}
					if (jsonObj.containsKey(Keyword.PLACE)) movieActivity.setPlace(jsonObj.getString(Keyword.PLACE));
					if (jsonObj.containsKey(Keyword.CONTACT)) movieActivity.setContact(jsonObj.getString(Keyword.CONTACT));
					if (jsonObj.containsKey(Keyword.CONTENT)) movieActivity.setContent(jsonObj.getString(Keyword.CONTENT));
					if (jsonObj.containsKey(Keyword.JOIN_BOUND)) movieActivity.setJoinBound(jsonObj.getInt(Keyword.JOIN_BOUND));
					if (jsonObj.containsKey(Keyword.STATE)) movieActivity.setState(jsonObj.getInt(Keyword.STATE));
					if (jsonObj.containsKey(Keyword.LONGITUDE)) movieActivity.setLongitude(new BigDecimal(jsonObj.getString(Keyword.LONGITUDE)));
					if (jsonObj.containsKey(Keyword.LATITUDE)) movieActivity.setLatitude(new BigDecimal(jsonObj.getString(Keyword.LATITUDE)));
					
					movieActivityRepository.save(movieActivity);
					map.put(Keyword.STATUS, Values.OK);
					return JSONObject.fromObject(map).toString();
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

