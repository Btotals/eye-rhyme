package com.controllers.user;

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

import com.models.MovieDescription;
import com.repositories.MovieDescriptionRepository;
import com.utils.Keyword;
import com.utils.Values;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class GetMoviesController {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	MovieDescriptionRepository movieDescriptionRepository;
	
    @RequestMapping(value="user/get_movies", method=RequestMethod.GET)
    public String getMovies() {
        return "this controller is for getting information of movies...";
    }
    
    @RequestMapping(value="user/get_movies", method=RequestMethod.POST)
    public String getMovies(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		if (jsonObj.containsKey(Keyword.MOVIE_ID)) {
				ArrayList<Integer> movieIds = new ArrayList<Integer>();
				JSONArray jsonArray = JSONArray.fromObject(jsonObj.getString(Keyword.MOVIE_ID));
				for (int i = 0; i < jsonArray.size(); i++) {
					movieIds.add((Integer) jsonArray.get(i));
				}
				
				// prepare the information about every movie..
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (Integer id: movieIds) {
				    MovieDescription movie = movieDescriptionRepository.findMovieDescriptionById(id);
					Map<String, String> tmap = new HashMap<String, String>();
			        tmap.put(Keyword.MOVIE_ID, id.toString());
			        tmap.put(Keyword.NAME, movie.getName());
			        tmap.put(Keyword.DURATION, movie.getDuration().toString());
			        tmap.put(Keyword.REALEASE_DATE, movie.getReleaseDate().toString());
			        tmap.put(Keyword.TYPE, movie.getType().toString());
			        tmap.put(Keyword.ACTORS, movie.getActors());
			        tmap.put(Keyword.DIRECTORS, movie.getDirectors());
			        tmap.put(Keyword.GRADE, getAverageGrade(id).toString());
			        
			        list.add(tmap);
				}
				
				map.put(Keyword.STATUS, Values.OK);
				map.put(Keyword.MOVIES, list);
	    		return JSONObject.fromObject(map).toString();
    		}
    	} catch(Exception exception) {
    		exception.printStackTrace();
    	}
    	
    	// otherwise, return wrong response..
    	map.put(Keyword.STATUS, Values.ERR);
    	return JSONObject.fromObject(map).toString();
    }
    
    public Double getAverageGrade(Integer id) {
    	String SQL = String.format("select AVG(value) from movie_grade where movie_description_id = %d", id);
		return jdbcTemplate.query(SQL, (rs, rowNum) -> new Double(rs.getDouble(1))).get(0);
    }
}
