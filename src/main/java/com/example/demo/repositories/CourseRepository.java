package com.example.demo.repositories;

import com.example.demo.models.Course;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("select u from Course u where u.idAuthor = ?1")
    List<Course> findByAuthor(String idAuthor);
}
