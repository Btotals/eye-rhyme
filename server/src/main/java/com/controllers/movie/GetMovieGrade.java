package com.controllers.movie;

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

import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

import com.modelboosters.Grade;

import net.sf.json.JSONObject;


@RestController
public class GetMovieGrade {
    
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @RequestMapping(value="movie/get_grades", method=RequestMethod.GET)
    public String getGrades() {
        return "this controller is for getting grades of a specific movie...";
    }
    
    @RequestMapping(value="movie/get_grades", method=RequestMethod.POST)
    public String getGrades(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.MOVIE_ID)) {
    			
    			String SQL = String.format("select user_id, value, content, date_time from movie_grade where movie_description_id = %d", jsonObj.getInt(Keyword.MOVIE_ID));
    			if (jsonObj.containsKey(Keyword.DATE_TIME)) SQL += String.format(" and date_time < \"%s\"", jsonObj.getString(Keyword.DATE_TIME));
    			SQL += " order by date_time DESC";
    			SQL += String.format(" limit %d", Values.GRADE_LIMIT);
    			
    			List<Grade> result = jdbcTemplate.query(SQL, (rs, rowNum) -> new Grade(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
    			// prepare the information about every theater..
    			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    			for (Grade grade: result) {
    				Map<String, String> tmap = new HashMap<String, String>();
    				String name = userRepository.findUserById(grade.getUserId()).getName();
    		        tmap.put(Keyword.NAME, name);
    		        tmap.put(Keyword.GRADE, grade.getValue().toString());
    		        tmap.put(Keyword.CONTENT, grade.getContent());
    		        tmap.put(Keyword.DATE_TIME, grade.getDataTime());
    		        list.add(tmap);
    			}
    			// JSONArray ja = JSONArray.fromObject(list);
    			
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
}
