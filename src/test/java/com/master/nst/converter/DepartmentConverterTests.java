package com.master.nst.converter;

import com.master.nst.converter.impl.DepartmentConverter;
import com.master.nst.domain.Department;
import com.master.nst.dto.DepartmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentConverterTests {

    @Autowired
    private DepartmentConverter departmentConverter;

    @Test
    public void toEntityTest(){
        DepartmentDto departmentDto = new DepartmentDto(1l, "Department1", "dept1");
        Department department = departmentConverter.toEntity(departmentDto);
        Assertions.assertEquals(departmentDto.getId(), department.getId());
        Assertions.assertEquals(departmentDto.getName(), department.getName());
        Assertions.assertEquals(departmentDto.getShort_name(), department.getShort_name());
    }

    @Test
    public void toDtoTest(){
        Department department = new Department(2l, "Department2", "dept2");
        DepartmentDto departmentDto = departmentConverter.toDto(department);
        Assertions.assertEquals(department.getId(), departmentDto.getId());
        Assertions.assertEquals(department.getName(), departmentDto.getName());
        Assertions.assertEquals(department.getShort_name(), departmentDto.getShort_name());
    }
}
