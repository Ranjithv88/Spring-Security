package com.SpringBoot.SpringSecurity.Repository;

import com.SpringBoot.SpringSecurity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}

