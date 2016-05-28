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
public class CheckValidtyController {
	@Autowired
	private AccountRepository accountRepository;
	
    @RequestMapping(value="account/check_validty", method=RequestMethod.GET)
    public String check() {
        return "this controller is for checking validty...";
    }
    
    @RequestMapping(value="account/check_validty", method=RequestMethod.POST)
    public String check(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		if (jsonObj.containsKey(Keyword.PHONE)) {
	    		// get the account by phone..
	    		Account account = accountRepository.findAccountByPhone(jsonObj.getString(Keyword.PHONE));
		    	// check the existence of this user..
	    		if (account != null) {
	    			map.put(Keyword.STATE, Values.ONE.toString());
	    			map.put(Keyword.USER_ID, account.getId().toString());
	    		} else {
	    			map.put(Keyword.STATE, Values.ZERO.toString());
	    		}
	    		map.put(Keyword.STATUS, Values.OK);
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
