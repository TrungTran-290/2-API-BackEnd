package com.example.demo.services;

import com.example.demo.dtos.StudentDto;
import com.example.demo.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService {

    String getName();
    Student getOne(Long id);
    List<Student> getAll();
    Page<Student> findAll(Pageable pageable);
    Student create(StudentDto studentDto);

    Student update(StudentDto studentDto, Long id);

    void delete(Long id);
    List<Student> search(String name);
    List<Student> findByName(String name);
}
