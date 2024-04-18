package com.master.nst.service;

import com.master.nst.converter.impl.DepartmentConverter;
import com.master.nst.domain.Department;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.DepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private DepartmentConverter departmentConverter;

    @Test
    public void saveSuccessTest(){
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        Department department = new Department(1l, "Department1", "dept1");
        when(departmentConverter.toEntity(departmentDto)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.empty());
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        DepartmentDto savedDto = departmentService.save(departmentDto);
        Assertions.assertNotNull(savedDto);
        Assertions.assertEquals(departmentDto, savedDto);
    }

    @Test
    public void saveFailureTest(){
        DepartmentDto departmentDto = new DepartmentDto(2l, "Department2", "dept2");
        Department department = new Department(2l, "Department2", "dept2");
        when(departmentConverter.toEntity(departmentDto)).thenReturn(department);
        when(departmentRepository.findByName(department.getName())).thenReturn(Optional.of(department));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            departmentService.save(departmentDto);
        });
    }

    @Test
    public void getAllTest(){
        Department department1 = new Department(4l, "Department4", "dept4");
        Department department2 = new Department(5l, "Department5", "dept5");
        List<Department> departments = Arrays.asList(department1, department2);

        //it is learned what to return
        when(departmentRepository.findAll()).thenReturn(departments);

        DepartmentDto departmentDto1 = new DepartmentDto(4l, "Department4", "dept4");
        DepartmentDto departmentDto2 = new DepartmentDto(5l, "Department5", "dept5");

        when(departmentConverter.toDto(department1)).thenReturn(departmentDto1);
        when(departmentConverter.toDto(department2)).thenReturn(departmentDto2);

        List<DepartmentDto> result = departmentService.getAll();

        for (int i = 0; i < departments.size(); i++) {
            Department expected = departments.get(i);
            DepartmentDto actual = result.get(i);
            Assertions.assertEquals(actual, departmentConverter.toDto(expected));
        }
    }

    @Test
    public void deleteSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        departmentService.delete(department.getId());
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    public void deleteFailureTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.delete(department.getId());
        });
    }

    @Test
    public void updateSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        department.setName("Department7");
        department.setShort_name("dept7");
        DepartmentDto departmentDto = new DepartmentDto(6l, "Department7", "dept7");
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        DepartmentDto actual = departmentService.update(departmentDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(departmentDto, actual);
    }

    @Test
    public void updateFailureTest(){
        Department department = new Department(6l, "Department6", "dept6");
        DepartmentDto departmentDto = new DepartmentDto(6l, "Department6", "dept6");
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.update(departmentDto);
        });
    }

    @Test
    public void findByIdSuccessTest(){
        Department department = new Department(6l, "Department6", "dept6");
        DepartmentDto departmentDto = new DepartmentDto(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(departmentConverter.toDto(department)).thenReturn(departmentDto);
        DepartmentDto actual = departmentService.findById(department.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(departmentDto, actual);
    }

    @Test
    public void findByIdFailureTest(){
        Department department = new Department(6l, "Department6", "dept6");
        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            departmentService.findById(department.getId());
        });

    }

    }


