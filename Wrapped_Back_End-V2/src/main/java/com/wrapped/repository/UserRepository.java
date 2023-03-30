package com.wrapped.repository;

import com.wrapped.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.password = ?2")
    User findUserByEmailAndPassword(String email, String password);
}
