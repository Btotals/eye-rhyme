package com.controllers.account;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.Account;
import com.models.User;
import com.repositories.AccountRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;


@RestController
public class RegisterController {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value="account/register", method=RequestMethod.GET)
    public String register(Model model) {
		return "this controller is for registering..";
    }
    
    @RequestMapping(value="account/register", method=RequestMethod.POST)
    public String register(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	// if error aroused in this place, we need to catch it directly.. 
    	try {
    		if (jsonObj.containsKey(Keyword.NAME) && jsonObj.containsKey(Keyword.PHONE) && jsonObj.containsKey(Keyword.PWD)) {
	    		// add a new account..
	    		Account account = accountRepository.save(new Account(jsonObj.getString(Keyword.PHONE), jsonObj.getString(Keyword.PWD)));
	    		if (account != null) {
	    			// get account's id..
	    			Integer id = account.getId();
	    			String phone = account.getPhone();
	    			// add a new user..
	    			User user = userRepository.save(new User(id, jsonObj.getString(Keyword.NAME), phone));
	    			if (user != null) {
	    				// return correct response..
	    				map.put(Keyword.STATUS, Values.OK);
	    				map.put(Keyword.ID, id.toString());
	    				map.put(Keyword.SALT, account.getSalt());
	    				return JSONObject.fromObject(map).toString();
	    			}
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
