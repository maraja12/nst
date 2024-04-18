package com.master.nst.repository;

import com.master.nst.domain.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentRepositoryTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void findByNameTest(){
        Department department = departmentRepository.save
                (new Department(1l, "Department1", "dept1"));
        assertNotNull(department);
        Optional<Department> dept = departmentRepository.findByName(department.getName());
        assertTrue(dept.isPresent());
        assertEquals(department, dept.get());
    }
}
