package com.example.demo.controllers;


import com.example.demo.models.Course;
import com.example.demo.models.User;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class CourseController {


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @GetMapping("/author/{id}")
    public List<Course> findAllByAuthor(@PathVariable(value = "id") long id) {
        return (List<Course>) courseRepository.findByAuthor(String.valueOf(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCourseById(@PathVariable(value = "id") long id) {
        // Implement
        Optional<Course> course = courseRepository.findById(id);
        Optional<User> user = userRepository.findById(Long.parseLong(course.get().getIdAuthor()));

        System.out.println(user.get().getName());

        HashMap< Object, Object > map = new HashMap<Object, Object>();
        map.put("course", course.get());
        map.put("user", user.get());

        if(course.isPresent()) {
            return ResponseEntity.ok().body(map); // status 200
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Course saveCourse(@Validated @RequestBody Course course) {
        // Implement

        return courseRepository.save(course);
    }



    @PostMapping("/update")
    public Course updateCourse(@Validated @RequestBody Course course) {
        Course course1 = courseRepository.findById(course.getId()).get();
        course1.setTitle(course.getTitle());
        course1.setCategory(course.getCategory());
        course1.setImg(course.getImg());
        course1.setContent(course.getContent());
        course1.setDescription(course.getDescription());
        course1.setVideo(course.getVideo());

        return courseRepository.save(course1);
    }


    @PostMapping("/delete")
    public boolean deleteCourse(@Validated @RequestBody String id) {
        courseRepository.deleteById(Long.valueOf(id));
        return true;
    }
}
