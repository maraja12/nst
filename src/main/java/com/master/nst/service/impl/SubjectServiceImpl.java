package com.master.nst.service.impl;

import com.master.nst.converter.impl.DepartmentConverter;
import com.master.nst.converter.impl.SubjectConverter;
import com.master.nst.domain.Department;
import com.master.nst.domain.Subject;
import com.master.nst.dto.SubjectDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.DepartmentRepository;
import com.master.nst.repository.SubjectRepository;
import com.master.nst.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;
    private SubjectConverter subjectConverter;
    private DepartmentRepository departmentRepository;
    private DepartmentConverter departmentConverter;

    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              SubjectConverter subjectConverter,
                              DepartmentRepository departmentRepository,
                              DepartmentConverter departmentConverter) {
        this.subjectRepository = subjectRepository;
        this.subjectConverter = subjectConverter;
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
    }

    @Override
    public List<SubjectDto> getAll() {
        return subjectRepository.findAll()
                .stream().map(entity -> subjectConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<Subject> subj = subjectRepository.findById(id);
        if(subj.isPresent()){
            Subject subject = subj.get();
            subjectRepository.delete(subject);
        }
        else{
            throw new EntityNotFoundException("Subject with id = " +
                    id + " does not exist!");
        }
    }

    @Override
    public SubjectDto save(SubjectDto subjectDto) throws EntityAlreadyExistsException {
        //first check if subject with this name already exists
        Optional<Subject> subj = subjectRepository.findByName(subjectDto.getName());
        if(subj.isPresent()){
            throw new EntityAlreadyExistsException("Subject with name = " +
                    subjectDto.getName() + " already exists!");
        }
        else{
            Subject subject = subjectConverter.toEntity(subjectDto);
            if(subject.getDepartment().getId() == null){
                departmentRepository.save(subject.getDepartment());
            }
            else{
                Optional<Department> dept = departmentRepository.findById(subject.getDepartment().getId());
                if(dept.isEmpty()){
                    departmentRepository.save(subject.getDepartment());
                }
            }
            subject = subjectRepository.save(subject);
            return subjectConverter.toDto(subject);
        }
    }

    @Override
    public SubjectDto update(SubjectDto subjectDto) throws EntityNotFoundException {
        //subj -> subject with old values
        Optional<Subject> subj = subjectRepository.findById(subjectDto.getId());
        if(subj.isPresent()){
            Subject subject = subj.get();
            //new department
            Department department = departmentConverter.toEntity(subjectDto.getDepartmentDto());
            if(department.getId() == null){
                departmentRepository.save(department);
            }
            else{
                Optional<Department> dept = departmentRepository.findById(department.getId());
                if(dept.isEmpty()){
                    departmentRepository.save(department);
                }
            }
            subject.setName(subjectDto.getName());
            subject.setEspb(subjectDto.getEspb());
            subject.setDepartment(department);
            subject = subjectRepository.save(subject);
            return subjectConverter.toDto(subject);
        }
        else{
            throw new EntityNotFoundException("Subject with id = " +
                    subjectDto.getId() + " does not exist!");
        }
    }

    @Override
    public SubjectDto findById(Long id) throws EntityNotFoundException {
        Optional<Subject> subj = subjectRepository.findById(id);
        if(subj.isPresent()){
            Subject subject = subj.get();
            return subjectConverter.toDto(subject);
        }
        else{
            throw new EntityNotFoundException("Subject with id = " +
                    id + " does not exist!");
        }
    }
}
