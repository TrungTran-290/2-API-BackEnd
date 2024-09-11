package com.example.demo.services;

import com.example.demo.dtos.StudentDto;
import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IService {

    private final StudentRepo repo;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Student getOne(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAll() {
        return repo.findAll();
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Student create(StudentDto studentDto) {
        Student student = Student.builder()
                .name(studentDto.getName())
                .thanhpho(studentDto.getThanhpho())
                .ngaysinh(studentDto.getNgaysinh())
                .xeploai(studentDto.getXeploai())
                .build();
                return repo.save(student);
    }

    @Override
    public Student update(StudentDto studentDto, Long id) {
        Student student = repo.findById(id).orElse(null);
        student.setName(studentDto.getName());
        student.setThanhpho(studentDto.getThanhpho());
        student.setNgaysinh(studentDto.getNgaysinh());
        student.setXeploai(studentDto.getXeploai());
        return repo.save(student);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Student> search(String name) {
        return repo.search(name);
    }

    @Override
    public List<Student> findByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }
}
