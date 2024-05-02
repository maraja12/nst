package com.master.nst.service;

import com.master.nst.domain.MemberRole;
import com.master.nst.dto.DepartmentHistoryDto;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface DepartmentHistoryService {

    List<DepartmentHistoryDto> getAll();
    void delete(Long id) throws EntityNotFoundException;
    DepartmentHistoryDto findById(Long id) throws EntityNotFoundException;
    List<DepartmentHistoryDto> findByMemberRole(String role) throws EntityNotFoundException;
    List<DepartmentHistoryDto> findByDepartmentId(Long id) throws EntityNotFoundException;
}
