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
import com.models.User;
import com.repositories.AccountRepository;
import com.repositories.UserRepository;
import com.utils.Keyword;
import com.utils.Values;

@RestController
public class UpdateAccountController {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping(value="account/update", method=RequestMethod.GET)
    public String update() {
        return "this controller is for updating account...";
    }
    
    @RequestMapping(value="account/update", method=RequestMethod.POST)
    public String update(@RequestBody JSONObject jsonObj) {
    	
    	Map<String, String> map = new HashMap<String, String>();
    	try {
    		// get the salt from account..
    		if (jsonObj.containsKey(Keyword.ID)) {
    			Account account = accountRepository.findAccountById(new Integer(jsonObj.getString(Keyword.ID)));
    			if (account != null) {
    				if (jsonObj.containsKey(Keyword.PHONE)) {
    					String phone = jsonObj.getString(Keyword.PHONE);
    					account.setPhone(phone);
    					// change the user' phone meantime.
    					User user = userRepository.findUserById(account.getId());
    					user.setPhone(phone);
    					userRepository.save(user);
    				}
    				
    				if (jsonObj.containsKey(Keyword.PWD)) account.setPassword(jsonObj.getString(Keyword.PWD));
    				// save changes..
    				accountRepository.save(account);
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
