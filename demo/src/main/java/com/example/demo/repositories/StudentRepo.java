package com.example.demo.repositories;

import com.example.demo.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);
    List<Student> findByNameContainingIgnoreCase(String name);
    @Query("SELECT s FROM Student s WHERE LOWER(s.thanhpho) LIKE lower(concat('%',:name,'%')) or lower(s.name) like lower(concat('%',:name,'%'))")
    List<Student> search(String name);

}
