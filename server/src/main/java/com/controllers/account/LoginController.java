package com.controllers.account;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.models.Account;
import com.repositories.AccountRepository;
import com.utils.Keyword;
import com.utils.Values;

@RestController
public class LoginController {
	@Autowired
	private AccountRepository accountRepository;
	
    @RequestMapping(value="account/login", method=RequestMethod.GET)
    public String login() {
        return "this controller is for logining";
    }
    
    @RequestMapping(value="account/login", method=RequestMethod.POST)
    public String login(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		if (jsonObj.containsKey(Keyword.PHONE) && jsonObj.containsKey(Keyword.PWD)) {
	    		// get the account..
	    		Account account = accountRepository.findAccountByPhone(jsonObj.getString(Keyword.PHONE));
		    	// check the password..
		    	if (account != null && jsonObj.getString(Keyword.PWD).equals(account.getPassword())) {
		    		// return correct response..
		    		map.put(Keyword.STATUS, Values.OK);
		    		map.put(Keyword.ID, account.getId().toString());
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

/*public String login(@RequestBody JSONObject jsonObj) {
	try {
	
		System.out.println(jsonObj.getInt("id"));
		System.out.println(jsonObj.getString("id"));
		System.out.println(jsonObj.getString("id").getClass().getName());
		
		return jsonObj.toString();
	} catch(Exception exception) {
		exception.printStackTrace();
	}
	return "hehe";
}*/
