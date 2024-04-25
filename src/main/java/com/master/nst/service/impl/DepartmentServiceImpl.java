package com.master.nst.service.impl;

import com.master.nst.converter.impl.DepartmentConverter;
import com.master.nst.domain.Department;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.DepartmentRepository;
import com.master.nst.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

private DepartmentRepository departmentRepository;
private DepartmentConverter departmentConverter;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 DepartmentConverter departmentConverter) {
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
    }


    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        Optional<Department> dept = departmentRepository.findByName(departmentDto.getName());
        if(dept.isPresent()){
            throw new EntityAlreadyExistsException(
                    "Department with name = " + departmentDto.getName() +
                    " already exists!");
        }
        else{
            Department department = departmentConverter.toEntity(departmentDto);
            department = departmentRepository.save(department);
            return departmentConverter.toDto(department);
        }
    }

    @Override
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll()
                .stream().map(entity -> departmentConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<Department> dept = departmentRepository.findById(id);
        if(dept.isPresent()){
            Department department = dept.get();
            departmentRepository.delete(department);
        }
        else{
            throw new EntityNotFoundException(
                    "Department with id = " + id +
                    " does not exist!");
        }
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto) throws EntityNotFoundException {
        Optional<Department> dept = departmentRepository.findById(departmentDto.getId());
        if(dept.isPresent()){
            Department department = dept.get();
            department.setName(departmentDto.getName());
            department.setShort_name(departmentDto.getShort_name());
            department = departmentRepository.save(department);
            return departmentConverter.toDto(department);
        }
        else{
            throw new EntityNotFoundException(
                    "Department with id = " + departmentDto.getId() +
                            " does not exist!");
        }

    }

    @Override
    public DepartmentDto findById(Long id) throws EntityNotFoundException {
        Optional<Department> dept = departmentRepository.findById(id);
        if(dept.isPresent()){
            Department department = dept.get();
            return departmentConverter.toDto(department);
        }
        else{
            throw new EntityNotFoundException(
                    "Department with id = " + id +
                            " does not exist!");
        }
    }
}
