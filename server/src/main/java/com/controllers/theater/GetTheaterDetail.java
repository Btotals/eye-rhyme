package com.controllers.theater;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.Theater;
import com.repositories.TheaterRepository;
import com.utils.Keyword;
import com.utils.Values;
import net.sf.json.JSONObject;

@RestController
public class GetTheaterDetail {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	TheaterRepository theaterRepository;
	
    @RequestMapping(value="theater/get_theaer_detail", method=RequestMethod.GET)
    public String getTheaterInfo() {
        return "this controller is for getting information of a specific theater...";
    }
    
    @RequestMapping(value="theater/get_theaer_detail", method=RequestMethod.POST)
    public String getTheaterInfo(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.THEATER_ID)) {
    			Theater theater = theaterRepository.findTheaterById(jsonObj.getInt(Keyword.THEATER_ID));
    			if (theater != null) {
    			
	    			// prepare the information about every theater..
	    			Map<String, String> map2 = new HashMap<String, String>();
	    			map2.put(Keyword.THEATER_ID, theater.getId().toString());
	    			map2.put(Keyword.NAME, theater.getName());
	    			map2.put(Keyword.LOCATION, theater.getLocation());
	    			map2.put(Keyword.CONTACT, theater.getContact());
	    			map2.put(Keyword.DESCRIPTION, theater.getDescription());
	    			map2.put(Keyword.LONGITUDE, theater.getLongitude().toString());
	    			map2.put(Keyword.LATITUDE, theater.getLatitude().toString());
	    			map2.put(Keyword.GRADE, getAverageGrade(theater.getId()).toString());
	    			map2.put(Keyword.GRADE_NUM, getGradeNum(theater.getId()).toString());
	    			
	    			map.put(Keyword.STATUS, Values.OK);
	    			map.put(Keyword.RESULT, map2);
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
    
    // get this theater's average grade from users..
    public Double getAverageGrade(Integer id) {
    	String SQL = String.format("select AVG(value) from theater_grade where theater_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Double(rs.getDouble(1))).get(0);
    }
    
    // get the number of graders..
    public Integer getGradeNum(Integer id) {
    	String SQL = String.format("select COUNT(user_id) from theater_grade where theater_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1))).get(0);
    }
}
