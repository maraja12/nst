package com.master.nst.service;


import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.exception.MemberNotBelongToDepartmentException;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();
    DepartmentDto save(DepartmentDto departmentDto) throws EntityAlreadyExistsException;
    void delete(Long id) throws EntityNotFoundException;
    DepartmentDto update(DepartmentDto departmentDto) throws EntityNotFoundException;
    DepartmentDto findById(Long id) throws EntityNotFoundException;

    void setManager(Long departmentId, Long memberId) throws EntityNotFoundException;
    void setSecretary(Long departmentId, Long memberId)
            throws EntityNotFoundException, MemberNotBelongToDepartmentException,
            EntityAlreadyExistsException;
}
