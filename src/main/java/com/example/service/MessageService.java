package com.example.service;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import antlr.StringUtils;

@Service
@Transactional
public class MessageService {


   public MessageRepository msgRepo;
   public AccountRepository acctRepo;

   @Autowired
   public MessageService(MessageRepository msgRepo, AccountRepository acctRepo){
        this.msgRepo = msgRepo;
        this.acctRepo = acctRepo;
   } 

   public Message createMessage(Message msg){

    if (msg.getMessage_text().length() > 0 && msg.getMessage_text().length() < 255) {
        if (acctRepo.findById(msg.getPosted_by()).isPresent()) {
            return msgRepo.save(msg);
        }
    }
    return null;
}
   
    public List<Message> getAllMessage(){
        return msgRepo.findAll();
    }
    

    public Message getMessageByMessageId(int message_id) {
        Optional <Message> ms = msgRepo.findById(message_id);
        if (ms.isPresent()) {
            return ms.get();
        }
        return null;
    }

    public List<Message> getMessageByMessageUser(int posted_by){
        return msgRepo.getMessagesByUser(posted_by);
    }

    public void deleteMessageById(int message_id){
        Optional<Message> ms = msgRepo.findById(message_id);
        if (ms.isPresent()) {
         msgRepo.deleteById(message_id);
        }
         
     }

    public Message UpdateMessageById(int message_id, String messageText){
        if (messageText.length() == 0 || messageText.length() > 255) {
            return null;
        }
        Optional<Message> message = msgRepo.findById(message_id);
        if (message.isPresent()) {
            Message msg = message.get();
            msg.setMessage_text(messageText);
            return msgRepo.save(msg); 
        }
        return null;
    }
    

}
