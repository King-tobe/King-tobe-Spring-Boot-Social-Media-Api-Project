package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientError;
import com.example.exception.Conflict;
import com.example.exception.Unauthorized;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    
    public AccountRepository acctRepostitory;

    @Autowired
    public AccountService(AccountRepository acctRepostitory){
        this.acctRepostitory = acctRepostitory;
    }

    
    public Account createUser(Account acct){
    
        if (acctRepostitory.checkUserName(acct.getUsername()) != null) {
            return null;
        }
        return acctRepostitory.save(acct);
    }

    public Account login(Account acct){
        String username = acct.getUsername();
        String password = acct.getPassword();
        
        Account loginUser = acctRepostitory.findByUsernameAndPassword(username, password);

        if (loginUser != null) {
            return loginUser;
        }else{
            return null;
        }
   }

   
 
}
