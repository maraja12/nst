package com.master.nst.repository;

import com.master.nst.domain.Department;
import com.master.nst.domain.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class SubjectRepositoryTests {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void findByNameTest(){
        Department department = new Department(1l, "Department1", "dept1");
        departmentRepository.save(department);
        Subject subject = new Subject(1l, "Maths", 5, department);
        Subject saved = subjectRepository.save(subject);
        Assertions.assertNotNull(saved);
        Optional<Subject> found = subjectRepository.findByName(saved.getName());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(subject, found.get());
    }
}
