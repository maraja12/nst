package com.master.nst.service;


import com.master.nst.domain.Department;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();
    DepartmentDto save(DepartmentDto departmentDto) throws EntityAlreadyExistsException;
    void delete(Long id) throws EntityNotFoundException;
    DepartmentDto update(DepartmentDto departmentDto) throws EntityNotFoundException;
    DepartmentDto findById(Long id) throws EntityNotFoundException;

}
