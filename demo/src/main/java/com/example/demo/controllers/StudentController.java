package com.example.demo.controllers;

import com.example.demo.dtos.StudentDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Student;
import com.example.demo.responses.StudentPagesResponse;
import com.example.demo.responses.StudentRes;
import com.example.demo.responses.StudentResponse;
import com.example.demo.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @GetMapping("/GetOne")
    public  Student getStudent(@RequestParam Long id) {
        return studentService.getOne(id);
    }
    @GetMapping("")
    public String getStudent() {
        return "Hello";
    }
    @GetMapping("/GetAll")
    public List<Student> getAllStudent() {
        return studentService.getAll();
    }
    @PostMapping("/Create")
    public ResponseEntity<?> createStudent(@RequestBody StudentDto studentDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors);
        }
         studentService.create(studentDto);
        return ResponseEntity.ok("insert" +studentDto);
    }
    @PostMapping("/Create2")
    public Student createStudent(@RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }
    @PostMapping("/Create3")
    public ResponseEntity<?> createStudent2(@Valid @RequestBody StudentDto studentDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList();
            StudentRes res = StudentRes.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Bad request")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(res);
        }
        Student student = studentService.create(studentDto);
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.OK.value())
                .message("Insert success")
                .data(student)
                .build();
            return ResponseEntity.ok("Insert" + res.toString());
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto,@PathVariable Long id,BindingResult result) {
//        return studentService.update(studentDto, id);
        if(result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors);
        }
        Student student = studentService.update(studentDto, id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.OK.value())
                .message("Update success")
                .data(studentService.update(studentDto, id))
                .build();
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Student student = studentService.getOne(id);
        if(student==null) {
            throw new ResourceNotFoundException("Student not found");
        }
        studentService.delete(id);
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.OK.value())
                .message("Delete success")
                .data(null)
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/search")
    public List<Student> findByName(@RequestParam String name) {
        return studentService.findByName(name);
    }

    @GetMapping("/pages")
    public ResponseEntity<?> getStudentPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentService.findAll(pageable);
        List<StudentResponse> studentResponseList = studentPage.getContent().stream().map(StudentResponse::from).toList();
        StudentPagesResponse studentPagesResponse = StudentPagesResponse.builder()
                .studentResponseList(studentResponseList)
                .totalPages(studentPage.getTotalPages())
                .build();
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.OK.value())
                .message("Get success")
                .data(studentPagesResponse)
                .build();
        return ResponseEntity.ok(res);
    }
}
