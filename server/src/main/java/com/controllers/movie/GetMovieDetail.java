package com.controllers.movie;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.MovieDescription;
import com.repositories.MovieDescriptionRepository;
import com.utils.Keyword;
import com.utils.Values;
import net.sf.json.JSONObject;

@RestController
public class GetMovieDetail {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	MovieDescriptionRepository  movieDescriptionRepository;
	
    @RequestMapping(value="movie/get_movie_detail", method=RequestMethod.GET)
    public String getMovieInfo() {
        return "this controller is for getting detailed information of a specific movie...";
    }
    
    @RequestMapping(value="movie/get_movie_detail", method=RequestMethod.POST)
    public String getMovieInfo(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.MOVIE_ID)) {
    			MovieDescription movie = movieDescriptionRepository.findMovieDescriptionById(jsonObj.getInt(Keyword.MOVIE_ID));
    			if (movie != null) {
	    			// prepare the information about every theater..
	    			Map<String, String> map2 = new HashMap<String, String>();
	    			map2.put(Keyword.NAME, movie.getName());
	    			map2.put(Keyword.DURATION, movie.getDuration().toString());
	    			map2.put(Keyword.REALEASE_DATE, movie.getReleaseDate().toString());
	    			map2.put(Keyword.TYPE, movie.getType().toString());
	    			map2.put(Keyword.ACTORS, movie.getActors());
	    			map2.put(Keyword.DIRECTORS, movie.getDirectors());
	    			map2.put(Keyword.DESCRIPTION, movie.getDescription());
	    			map2.put(Keyword.GRADE, getAverageGrade(movie.getId()).toString());
	    			
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
    	String SQL = String.format("select AVG(value) from movie_grade where movie_description_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Double(rs.getDouble(1))).get(0);
    }

}
