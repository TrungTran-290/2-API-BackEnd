package com.example.demo.responses;

import com.example.demo.models.Student;
import com.example.demo.models.XepLoai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentResponse {
    private Long id;
    private String name;
    private String thanhpho;
    private Date ngaysinh;
    private XepLoai xeploai;

    public static StudentResponse from(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .thanhpho(student.getThanhpho())
                .ngaysinh(student.getNgaysinh())
                .xeploai(student.getXeploai())
                .build();
    }
}
