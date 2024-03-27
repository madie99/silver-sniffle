package com.example.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BlogApplication.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByName(String theUserName) ;
}
