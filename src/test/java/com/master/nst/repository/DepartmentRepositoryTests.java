package com.master.nst.repository;

import com.master.nst.domain.Department;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DepartmentRepositoryTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @Transactional
    public void findByNameTest(){
        Department department = new Department(1l, "Department1", "dept1");
        Department saved = departmentRepository.save(department);
        assertNotNull(saved);
        Optional<Department> found = departmentRepository.findByName(saved.getName());
        assertTrue(found.isPresent());
        assertEquals(department, found.get());
    }
}
