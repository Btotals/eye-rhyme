package com.controllers.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.repositories.MovieProductRepository;
import com.repositories.MovieTicketRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;
import com.models.MovieTicket;
import net.sf.json.JSONObject;

@RestController
public class GetOrderController {
	
	@Autowired
	MovieProductRepository movieProductRepository;
	
	@Autowired
	MovieTicketRepository movieTicketRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="user/get_orders", method=RequestMethod.GET)
    public String getOrder() {
        return "this controller is for getting orders about one specific user..";
    }
    
    @RequestMapping(value="user/get_orders", method=RequestMethod.POST)
    public String getOrder(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		if (jsonObj.containsKey(Keyword.USER_ID)) {
    			ArrayList<MovieTicket> movieTickets;
    			
    			if (jsonObj.containsKey(Keyword.TICKET_ID)) movieTickets = (ArrayList<MovieTicket>) movieTicketRepository.findTop10ByUserIdAndStatusEqualsAndIdLessThanOrderByIdDesc(jsonObj.getInt(Keyword.USER_ID), Values.ONE, jsonObj.getInt(Keyword.TICKET_ID));
    			else movieTickets = (ArrayList<MovieTicket>) movieTicketRepository.findTop10ByUserIdAndStatusEqualsOrderByIdDesc(jsonObj.getInt(Keyword.USER_ID), Values.ONE);
    			
    			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
    			for (MovieTicket movieTicket: movieTickets) {
				    Map<String, Object> tmap = new HashMap<String, Object>();
			        tmap.put(Keyword.MOVIE_ID, movieTicket.getMovieProduct().getMovieDescription().getId());
			        tmap.put(Keyword.TICKET_ID, movieTicket.getId());
			        tmap.put(Keyword.MOVIE_NAME, movieTicket.getMovieProduct().getMovieDescription().getName());
			        tmap.put(Keyword.THEATER_NAME, movieTicket.getMovieProduct().getTheater().getName());
			        tmap.put(Keyword.DATE_TIME, movieTicket.getMovieProduct().getDate().toString());
			        tmap.put(Keyword.ROUND, movieTicket.getMovieProduct().getRound());
			        tmap.put(Keyword.RECEIPT_NUM, movieTicket.getReceiptNum());
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
}
