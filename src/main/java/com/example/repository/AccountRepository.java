package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("SELECT p FROM Account p WHERE p.username = :username AND p.password = :password")
    Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    @Query("FROM Account WHERE username = :username")
    Account checkUserName(@Param("username") String username);
}
