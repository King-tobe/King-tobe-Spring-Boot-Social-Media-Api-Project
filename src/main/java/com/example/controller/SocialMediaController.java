package com.example.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ClientError;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService acctService;
    MessageService msgService;

    @Autowired
    public SocialMediaController(AccountService acctService, MessageService msgService) {
        this.acctService = acctService;
        this.msgService = msgService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createUser(@RequestBody Account acct) {

        if (acct.getUsername().isBlank() || acct.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }
        Account Acct1 = acctService.createUser(acct);
        if (Acct1 != null) {
            return ResponseEntity.ok(Acct1);
        } else {
            return ResponseEntity.status(409).build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account acct) {

        Account loginIn = acctService.login(acct);
        if (loginIn != null) {
            return ResponseEntity.ok(loginIn);
        }
        return ResponseEntity.status(401).build();

    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        List<Message> msgs = msgService.getAllMessage();
        return msgs;
    }
        

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message msg) {
        Message message = msgService.createMessage(msg);
        if (message != null) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageByMessageID(@PathVariable int message_id){
        Message msg = msgService.getMessageByMessageId(message_id);
        if (msg != null) {
            return ResponseEntity.ok(msg);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("accounts/{account_id}/messages")
        public List<Message> getMessageByMessageUserId(@PathVariable int account_id){
        return msgService.getMessageByMessageUser(account_id);
    }

    @DeleteMapping("messages/{message_id}")
        public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable int message_id){
            int before = msgService.getAllMessage().size();
            msgService.deleteMessageById(message_id);
            int after = msgService.getAllMessage().size();
            if (before - after > 0) {
                return ResponseEntity.ok(before-after);
            }
            return ResponseEntity.status(200).build();
        }

    @PatchMapping("messages/{message_id}")
        public ResponseEntity<Integer> updateMessageByMessageId(@PathVariable int message_id, @RequestBody Message message){
            String st = message.getMessage_text();
            Message msg = msgService.UpdateMessageById(message_id, st);
            if (msg != null) {
                return ResponseEntity.ok(1);
            }
            return ResponseEntity.status(HttpStatus.valueOf(400)).build();
        }
    
    
}
