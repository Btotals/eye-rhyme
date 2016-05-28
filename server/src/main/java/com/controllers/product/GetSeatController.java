package com.controllers.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.repositories.MovieProductRepository;
import com.utils.Keyword;
import com.utils.Values;

import com.models.MovieProduct;

import net.sf.json.JSONObject;

@RestController
public class GetSeatController {
	
	@Autowired
	MovieProductRepository movieProductRepository;
	
    @RequestMapping(value="movie_product/get_seats", method=RequestMethod.GET)
    public String getMovieProduct() {
        return "this controller is for getting seats' information about one movie product..";
    }
    
    @RequestMapping(value="movie_product/get_seats", method=RequestMethod.POST)
    public String getMovieProduct(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.PRODUCT_ID)) {
    			MovieProduct movieProduct = movieProductRepository.findMovieProductById(jsonObj.getInt(Keyword.PRODUCT_ID));
    			
    			map.put(Keyword.STATUS, Values.OK);
    			map.put(Keyword.RESULT, movieProduct.getSeats());
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
