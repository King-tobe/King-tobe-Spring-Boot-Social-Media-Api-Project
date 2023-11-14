package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;
import com.example.entity.Message;


public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query("FROM Account a WHERE a.account_id = :account_id")
    Account addUser(@Param("account_id") int account_id);
    
    @Query("FROM Message a WHERE a.posted_by = :posted_by")
    List<Message> getMessagesByUser(@Param("posted_by") int posted_by);

    

}
