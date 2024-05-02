package com.master.nst.service.impl;

import com.master.nst.converter.impl.DepartmentHistoryConverter;
import com.master.nst.domain.Department;
import com.master.nst.domain.DepartmentHistory;
import com.master.nst.domain.MemberRole;
import com.master.nst.dto.DepartmentHistoryDto;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.DepartmentHistoryRepository;
import com.master.nst.repository.DepartmentRepository;
import com.master.nst.service.DepartmentHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentHistoryServiceImpl implements DepartmentHistoryService {

    private DepartmentHistoryRepository departmentHistoryRepository;
    private DepartmentHistoryConverter departmentHistoryConverter;

    private DepartmentRepository departmentRepository;

    public DepartmentHistoryServiceImpl(DepartmentHistoryRepository departmentHistoryRepository,
                                        DepartmentHistoryConverter departmentHistoryConverter,
                                        DepartmentRepository departmentRepository) {
        this.departmentHistoryRepository = departmentHistoryRepository;
        this.departmentHistoryConverter = departmentHistoryConverter;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentHistoryDto> getAll() {
        return departmentHistoryRepository.findAll()
                .stream().map(entity -> departmentHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<DepartmentHistory> deptHistory = departmentHistoryRepository.findById(id);
        if(deptHistory.isPresent()){
            DepartmentHistory departmentHistory = deptHistory.get();
            departmentHistoryRepository.delete(departmentHistory);
        }
        else{
            throw new EntityNotFoundException(
                    "Department history with id = " + id + " does not exist!");
        }
    }

    @Override
    public DepartmentHistoryDto findById(Long id) throws EntityNotFoundException {
        Optional<DepartmentHistory> deptHistory = departmentHistoryRepository.findById(id);
        if(deptHistory.isPresent()){
            DepartmentHistory departmentHistory = deptHistory.get();
            return departmentHistoryConverter.toDto(departmentHistory);
        }
        else{
            throw new EntityNotFoundException(
                    "Department history with id = " + id + " does not exist!");
        }
    }

    @Override
    public List<DepartmentHistoryDto> findByMemberRole(String role) throws EntityNotFoundException {
        role = role.toUpperCase();
        MemberRole memberRole = MemberRole.valueOf(role);
        Optional<List<DepartmentHistory>> deptHistory = departmentHistoryRepository.findByRole(memberRole);
        if(deptHistory.isPresent()){
            List<DepartmentHistory> departmentHistories = deptHistory.get();
            return departmentHistories.stream().map(entity -> departmentHistoryConverter.toDto(entity))
                    .collect(Collectors.toList());
        }
        else{
            throw new EntityNotFoundException(
                    "There is no " + role + " history for any department!");
        }
    }

    @Override
    public List<DepartmentHistoryDto> findByDepartmentId(Long id) throws EntityNotFoundException {
        Optional<Department> dept = departmentRepository.findById(id);
        if(dept.isPresent()){
            Department department = dept.get();
            Optional<List<DepartmentHistory>> deptHistory = departmentHistoryRepository.findByDepartment(department);
            if(deptHistory.isPresent()){
                List<DepartmentHistory> departmentHistories = deptHistory.get();
                return departmentHistories.stream().map(entity -> departmentHistoryConverter.toDto(entity))
                        .collect(Collectors.toList());
            }
            else{
                throw new EntityNotFoundException(
                        "Department history for department with id = " + id + " does not exist!");
            }
        }
        else{
            throw new EntityNotFoundException(
                    "Department with id = " + id + " does not exist!");
        }
    }
}
