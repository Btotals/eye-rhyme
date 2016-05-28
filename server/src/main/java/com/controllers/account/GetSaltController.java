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
public class GetSaltController {
	@Autowired
	private AccountRepository accountRepository;
	
    @RequestMapping(value="account/get_salt", method=RequestMethod.GET)
    public String get_salt() {
        return "this controller is for getting salt...";
    }
    
    @RequestMapping(value="account/get_salt", method=RequestMethod.POST)
    public String get_salt(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.PHONE)) {
    			Account account = accountRepository.findAccountByPhone(jsonObj.getString(Keyword.PHONE));
    			if (account != null) {
					// return correct response..
		    		map.put(Keyword.STATUS, Values.OK);
		    		map.put(Keyword.SALT, account.getSalt());
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
