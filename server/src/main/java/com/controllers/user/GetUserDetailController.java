package com.controllers.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.User;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;
import net.sf.json.JSONObject;

@RestController
public class GetUserDetailController {
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="user/get_user_detail", method=RequestMethod.GET)
    public String getUserDetail() {
        return "this controller is for getting detailed information about one user...";
    }
    
    @RequestMapping(value="user/get_user_detail", method=RequestMethod.POST)
    public String getUserDetail(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		if (jsonObj.containsKey(Keyword.USER_ID)) {
    			User user = userRepository.findUserById(jsonObj.getInt(Keyword.USER_ID));
    			if (user != null) {
	    			
    				map.put(Keyword.NAME, user.getName());
	    			map.put(Keyword.EMAIL, user.getEmail());
	    			if (user.getBirthday() != null) map.put(Keyword.BIRTHDAY, user.getBirthday().toString());
	    			else map.put(Keyword.BIRTHDAY, null);
	    			map.put(Keyword.GENDER, user.getGender().toString());
	    			map.put(Keyword.HOBBY, user.getHobby());
	    			map.put(Keyword.SIGNATURE, user.getSignature());
	    			map.put(Keyword.RANK, user.getRank().toString());
	    			
	    			map.put(Keyword.STATUS, Values.OK);
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

}

