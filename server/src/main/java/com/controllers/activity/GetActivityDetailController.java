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
import com.models.MovieDescription;
import com.models.User;
import com.repositories.MovieActivityRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;
import net.sf.json.JSONObject;

@RestController
public class GetActivityDetailController {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	MovieActivityRepository movieActivityRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="movie_activity/get_activity_detail", method=RequestMethod.GET)
    public String getActivityInfo() {
        return "this controller is for getting detailed information about a specific activity...";
    }
    
    @RequestMapping(value="movie_activity/get_activity_detail", method=RequestMethod.POST)
    public String getActivityInfo(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.ACTIVITY_ID)) {
    			MovieActivity movieActivity = movieActivityRepository.findMovieActivityById(jsonObj.getInt(Keyword.ACTIVITY_ID));
    			if (movieActivity != null) {
    				Map<String, Object> map0 = new HashMap<String, Object>();
    				Map<String, Object> map1 = new HashMap<String, Object>();
    				Map<String, Object> map2 = new HashMap<String, Object>();
    				Map<String, Object> map3 = new HashMap<String, Object>();
    				
    				// prepare the launcher's information..
    				User launcher = movieActivity.getLauncher();
    				map1.put(Keyword.USER_ID, launcher.getId().toString());
    				map1.put(Keyword.NAME, launcher.getName());
    				map1.put(Keyword.GENDER, launcher.getGender().toString());
    				map1.put(Keyword.RANK, launcher.getRank().toString());
	    			
    				// prepare the movie's information..
    				MovieDescription movieDescription = movieActivity.getMovieDescription();
	    			map2.put(Keyword.MOVIE_ID, movieDescription.getId().toString());
	    			map2.put(Keyword.MOVIE_NAME, movieDescription.getName());
	    			map2.put(Keyword.TYPE, movieDescription.getType());
	    			
	    			// prepare the activity's information..
	    			map3.put(Keyword.CONTENT, movieActivity.getContent());
	    			map3.put(Keyword.CONTACT, movieActivity.getContact());
	    			map3.put(Keyword.PLACE, movieActivity.getPlace());
	    			map3.put(Keyword.LONGITUDE, movieActivity.getLongitude());
	    			map3.put(Keyword.LATITUDE, movieActivity.getLatitude());
	    			map3.put(Keyword.JOIN_NUM, getJoinNum(movieActivity.getId()));
	    			map3.put(Keyword.JOIN_BOUND, movieActivity.getJoinBound());
	    			
	    			// prepare the users, involved in this activity..
	    			String SQL = String.format("select user_id from join_activity where activity_id = %d", movieActivity.getId());
	    			List<Integer> userIds = jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1)));
	    			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    			for (Integer id: userIds) {
	    				User user = userRepository.findUserById(id);
	    				Map<String, Object> tmap = new HashMap<String, Object>();
	    		        tmap.put(Keyword.USER_ID, id);
	    		        tmap.put(Keyword.NAME, user.getName());
	    		        list.add(tmap);
	    			}
	    			
	    			// prepare the final result..
	    			map0.put(Keyword.LAUNCHER_DETAIL, map1);
	    			map0.put(Keyword.MOVIE_DETAIL, map2);
	    			map0.put(Keyword.ACTIVITY_DETAIL, map3);
	    			map0.put(Keyword.JOIN_DETAIL, list);
	    			
	    			map.put(Keyword.STATUS, Values.OK);
	    			map.put(Keyword.RESULT, map0);
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
    
    // get the number of users, involved in this activity..
    public Integer getJoinNum(Integer id) {
    	String SQL = String.format("select COUNT(user_id) from join_activity where activity_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1))).get(0);
    }
    
}
