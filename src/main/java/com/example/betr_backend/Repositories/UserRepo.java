package com.example.betr_backend.Repositories;

import com.example.betr_backend.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findUserByUserId(long uid);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.passwordHash = :password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    @Transactional
    @Modifying
    @Query("DELETE FROM User u where u.userId=:uid")
    void deleteUser(@Param("uid") long uid);

}