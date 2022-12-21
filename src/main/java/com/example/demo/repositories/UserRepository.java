package com.example.demo.repositories;


import com.example.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT name, email, password, role, created_at FROM User WHERE email=:email")
    User getUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);
}
