package com.controllers.activity;

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

import com.models.MovieActivity;
import com.repositories.MovieActivityRepository;
import com.utils.GeographicCoordinateHelper;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class GetActivityController {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	MovieActivityRepository movieActivityRepository;
	
    @RequestMapping(value="movie_activity/get_activities", method=RequestMethod.GET)
    public String getActivities() {
        return "this controller is for getting information of activities nearby...";
    }
    
    @RequestMapping(value="movie_activity/get_activities", method=RequestMethod.POST)
    public String getActivities(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		if (jsonObj.containsKey(Keyword.LONGITUDE) && jsonObj.containsKey(Keyword.LATITUDE)) {
				// search the theater nearby within 20KM..
    			Double longtitude = jsonObj.getDouble(Keyword.LONGITUDE);
    			Double latitude = jsonObj.getDouble(Keyword.LATITUDE);
    			Double []locations = GeographicCoordinateHelper.getGeographicRange(longtitude, latitude, Values.DISTANCE);
    			
    			// following is the SQL's definitions here..
    			String SQL = String.format("select id from movie_activity where longitude < %f and longitude > %f and latitude < %f and latitude > %f and state = 0", 
    					locations[0], locations[1], locations[2], locations[3]);
    			if (jsonObj.containsKey(Keyword.ACTIVITY_ID)) SQL += String.format(" and id < %d", jsonObj.getInt(Keyword.ACTIVITY_ID));
    			SQL += " order by id DESC";
    			SQL += String.format(" limit %d", Values.LIMIT);
    			List<Integer> activityIds = jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1)));
    			
    			// prepare the information about every activity..
    			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    			for (Integer id: activityIds) {
    				MovieActivity movieActivity = movieActivityRepository.findMovieActivityById(id);
    				Map<String, String> tmap = new HashMap<String, String>();
    		        tmap.put(Keyword.ACTIVITY_ID, id.toString());
    		        tmap.put(Keyword.MOVIE_NAME, movieActivity.getMovieDescription().getName());
    		        tmap.put(Keyword.USER_ID, movieActivity.getLauncher().getId().toString());
    		        tmap.put(Keyword.NAME, movieActivity.getLauncher().getName());
    		        tmap.put(Keyword.DATE_TIME, movieActivity.getDateTime().toString());
    		        tmap.put(Keyword.PLACE, movieActivity.getPlace());
    		        tmap.put(Keyword.JOIN_NUM, getJoinNum(id).toString());
    		        tmap.put(Keyword.JOIN_BOUND, movieActivity.getJoinBound().toString());
    		        list.add(tmap);
    			}
    			
    			map.put(Keyword.STATUS, Values.OK);
    			map.put(Keyword.RESULT, list);
	    		return JSONObject.fromObject(map).toString();
    		}
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }
    
    public Integer getJoinNum(Integer id) {
    	String SQL = String.format("select COUNT(user_id) from join_activity where activity_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1))).get(0);
    }
    
}
