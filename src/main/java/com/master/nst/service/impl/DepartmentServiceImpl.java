package com.master.nst.service.impl;

import com.master.nst.converter.impl.DepartmentConverter;
import com.master.nst.domain.Department;
import com.master.nst.domain.DepartmentHistory;
import com.master.nst.domain.Member;
import com.master.nst.domain.MemberRole;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.exception.MemberNotBelongToDepartmentException;
import com.master.nst.repository.DepartmentHistoryRepository;
import com.master.nst.repository.DepartmentRepository;
import com.master.nst.repository.MemberRepository;
import com.master.nst.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

private DepartmentRepository departmentRepository;
private DepartmentConverter departmentConverter;
private MemberRepository memberRepository;

private DepartmentHistoryRepository departmentHistoryRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 DepartmentConverter departmentConverter,
                                 MemberRepository memberRepository,
                                 DepartmentHistoryRepository departmentHistoryRepository) {
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
        this.departmentHistoryRepository = departmentHistoryRepository;
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
            department.setShortName(departmentDto.getShortName());
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

    @Override
    public void setManager(Long departmentId, Long memberId) {
        Optional<Department> dept = departmentRepository.findById(departmentId);
        Optional<Member> mem = memberRepository.findById(memberId);
        if(dept.isPresent() && mem.isPresent()){
            Department department = dept.get();
            Member manager = mem.get();
            //check if this member belongs to this department
            if(manager.getDepartment().equals(department)){
                //if this department already has manager
                Optional<List<DepartmentHistory>> histories =
                        departmentHistoryRepository.findByDepartment(department);
                if(histories.isPresent()){
                    List<DepartmentHistory> departmentHistories = histories.get();
                    for (DepartmentHistory d : departmentHistories) {
                        if(d.getMember().equals(manager) && d.getEndDate() == null){
                            throw new EntityAlreadyExistsException(
                                    "This employee is already member of department management!"
                            );
                        }
                        if(d.getEndDate() == null && (d.getRole().equals(MemberRole.MANAGER))){
                            d.setEndDate(LocalDate.now());
                        }
                    }
                }
                    //Create new department history
                    DepartmentHistory departmentHistory = new DepartmentHistory(
                            null, LocalDate.now(), null, MemberRole.MANAGER, department, manager);
                    departmentHistoryRepository.save(departmentHistory);
                    department.setManager(manager);
                    departmentRepository.save(department);
            }
            else{
                throw new MemberNotBelongToDepartmentException(
                        "Member with id = "+ memberId + " does not belong to department with id = " + departmentId
                );
            }
            }
        else{
            throw new EntityNotFoundException("Nonexistent department or member id!");
        }
    }

    @Override
    public void setSecretary(Long departmentId, Long memberId) {

        Optional<Department> dept = departmentRepository.findById(departmentId);
        Optional<Member> mem = memberRepository.findById(memberId);
        if(dept.isPresent() && mem.isPresent()){
            Department department = dept.get();
            Member secretary = mem.get();
            //check if this member belongs to this department
            if(secretary.getDepartment().equals(department)){
                //if this department already has secretary
                Optional<List<DepartmentHistory>> histories =
                        departmentHistoryRepository.findByDepartment(department);
                if(histories.isPresent()){
                    List<DepartmentHistory> departmentHistories = histories.get();
                    for (DepartmentHistory d : departmentHistories) {
                        if(d.getMember().equals(secretary) && d.getEndDate() == null){
                            throw new EntityAlreadyExistsException(
                                    "This employee is already member of department management!"
                            );
                        }if(d.getEndDate() == null && (d.getRole().equals(MemberRole.SECRETARY))){
                            d.setEndDate(LocalDate.now());
                        }
                    }
                }
                    //Create new department history
                    DepartmentHistory departmentHistory = new DepartmentHistory(
                            null, LocalDate.now(), null, MemberRole.SECRETARY, department, secretary);
                    departmentHistoryRepository.save(departmentHistory);
                    department.setSecretary(secretary);
                    departmentRepository.save(department);
            }
            else{
                throw new MemberNotBelongToDepartmentException(
                        "Member with id = "+ memberId + " does not belong to department with id = " + departmentId
                );
            }
        }
        else{
            throw new EntityNotFoundException("Nonexistent department or member id!");
        }
    }
}
