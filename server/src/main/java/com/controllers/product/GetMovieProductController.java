package com.controllers.product;

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

import com.utils.Keyword;
import com.utils.Values;

import com.modelboosters.Product;

import net.sf.json.JSONObject;


@RestController
public class GetMovieProductController {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
    @RequestMapping(value="movie_product/get_products", method=RequestMethod.GET)
    public String getMovieProduct() {
        return "this controller is for getting information about one movie product..";
    }
    
    @RequestMapping(value="movie_product/get_products", method=RequestMethod.POST)
    public String getMovieProduct(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.MOVIE_ID) && jsonObj.containsKey(Keyword.THEATER_ID) && jsonObj.containsKey(Keyword.DATE)) {
    			
    			String SQL = String.format("select id, price, round, hall, type, discount from movie_product where movie_description = %d and theater = %d and date = \"%s\"",
    					jsonObj.getInt(Keyword.MOVIE_ID), jsonObj.getInt(Keyword.THEATER_ID), jsonObj.getString(Keyword.DATE));
    			
    			SQL += " order by round ASC";
    			
    			List<Product> result = jdbcTemplate.query(SQL, (rs, rowNum) -> new Product(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
    			// prepare the information about every theater..
    			List<String> list = new ArrayList<String>();
    			for (Product product: result) {
    				list.add(product.toJsonString());
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
