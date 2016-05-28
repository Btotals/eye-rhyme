package com.controllers.user;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.User;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

@RestController
public class UpdateUserController {
	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping(value="user/update", method=RequestMethod.GET)
    public String update() {
        return "this controller is for updating user's information...";
    }
    
    @RequestMapping(value="user/update", method=RequestMethod.POST)
    public String update(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.ID)) {
    			User user = userRepository.findUserById(new Integer(jsonObj.getString(Keyword.ID)));
    			if (user != null) {
    				if (jsonObj.containsKey(Keyword.BIRTHDAY)) {
    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    					user.setBirthday(sdf.parse(jsonObj.getString(Keyword.BIRTHDAY)));
    				}
    				if (jsonObj.containsKey(Keyword.EMAIL)) user.setEmail(jsonObj.getString(Keyword.EMAIL));
    				if (jsonObj.containsKey(Keyword.GENDER)) user.setGender(jsonObj.getInt(Keyword.GENDER));
    				if (jsonObj.containsKey(Keyword.HOBBY)) user.setHobby(jsonObj.getString(Keyword.HOBBY));
    				if (jsonObj.containsKey(Keyword.NAME)) user.setName(jsonObj.getString(Keyword.NAME));
    				if (jsonObj.containsKey(Keyword.SIGNATURE)) user.setSignature(jsonObj.getString(Keyword.SIGNATURE));
    				
    				// save changes..
    				userRepository.save(user);
    				// return correct response..
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
