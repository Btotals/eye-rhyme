package com.controllers.theater;

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

import com.models.Theater;
import com.repositories.TheaterRepository;
import com.utils.GeographicCoordinateHelper;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONObject;

@RestController
public class GetTheatersNearby {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	TheaterRepository theaterRepository;
	
	// private static final Logger log = LoggerFactory.getLogger(GetTheatersNearby.class);
	
    @RequestMapping(value="theater/get_theaters_nearby", method=RequestMethod.GET)
    public String getTheaters() {
        return "this controller is for getting information of theaters nearby...";
    }
    
    @RequestMapping(value="theater/get_theaters_nearby", method=RequestMethod.POST)
    public String getTheaters(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.LONGITUDE) && jsonObj.containsKey(Keyword.LATITUDE)) {
				// search the theater nearby within 20KM..
    			Double longtitude = jsonObj.getDouble(Keyword.LONGITUDE);
    			Double latitude = jsonObj.getDouble(Keyword.LATITUDE);
    			Double []locations = GeographicCoordinateHelper.getGeographicRange(longtitude, latitude, Values.DISTANCE);
    			
    			// following is the SQL's definitions here..
    			String SQL = String.format("select id from theater where longitude < %f and longitude > %f and latitude < %f and latitude > %f", 
    					locations[0], locations[1], locations[2], locations[3]);
    			if (jsonObj.containsKey(Keyword.THEATER_ID)) SQL += String.format(" and id > %d", jsonObj.getInt(Keyword.THEATER_ID));
    			SQL += " order by id ASC";
    			SQL += String.format(" limit %d", Values.LIMIT);
    			List<Integer> theaterIds = jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1)));
    			
    			// prepare the information about every theater..
    			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    			for (Integer id: theaterIds) {
    				Theater theater = theaterRepository.findTheaterById(id);
    				Map<String, String> tmap = new HashMap<String, String>();
    		        tmap.put(Keyword.THEATER_ID, id.toString());
    		        tmap.put(Keyword.NAME, theater.getName());
    		        tmap.put(Keyword.LOCATION, theater.getLocation());
    		        tmap.put(Keyword.LONGITUDE, theater.getLongitude().toString());
    		        tmap.put(Keyword.LATITUDE, theater.getLatitude().toString());
    		        tmap.put(Keyword.LOWEST_PRICE, getLowestPrice(id).toString());
    		        tmap.put(Keyword.GRADE, getAverageGrade(id).toString());
    		        list.add(tmap);
    			}
    			// JSONArray ja = JSONArray.fromObject(list);
    			
    			// log.info(theaterIds.toString());
    			map.put(Keyword.STATUS, Values.OK);
    			map.put(Keyword.THEATERS, list);
	    		return JSONObject.fromObject(map).toString();
    		}
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }
    
    public Integer getLowestPrice(Integer id) {
    	String SQL = String.format("select MIN(price) from movie_product where theater = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Integer(rs.getInt(1))).get(0);
    }
    
    public Double getAverageGrade(Integer id) {
    	String SQL = String.format("select AVG(value) from theater_grade where theater_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Double(rs.getDouble(1))).get(0);
    }
}
