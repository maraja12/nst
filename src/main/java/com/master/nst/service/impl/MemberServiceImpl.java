package com.master.nst.service.impl;

import com.master.nst.converter.impl.*;
import com.master.nst.domain.*;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.dto.MemberDto;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.*;
import com.master.nst.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private MemberConverter memberConverter;

    private DepartmentRepository departmentRepository;
    private DepartmentConverter departmentConverter;
    private AcademicTitleRepository academicTitleRepository;
    private AcademicTitleConverter academicTitleConverter;
    private ScientificFieldRepository scientificFieldRepository;
    private ScientificFieldConverter scientificFieldConverter;
    private EducationTitleRepository educationTitleRepository;
    private EducationTitleConverter educationTitleConverter;

    public MemberServiceImpl(MemberRepository memberRepository,
                             MemberConverter memberConverter,
                             DepartmentRepository departmentRepository,
                             DepartmentConverter departmentConverter,
                             AcademicTitleRepository academicTitleRepository,
                             AcademicTitleConverter academicTitleConverter,
                             ScientificFieldRepository scientificFieldRepository,
                             ScientificFieldConverter scientificFieldConverter,
                             EducationTitleRepository educationTitleRepository,
                             EducationTitleConverter educationTitleConverter) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
        this.academicTitleRepository = academicTitleRepository;
        this.academicTitleConverter = academicTitleConverter;
        this.scientificFieldRepository = scientificFieldRepository;
        this.scientificFieldConverter = scientificFieldConverter;
        this.educationTitleRepository = educationTitleRepository;
        this.educationTitleConverter = educationTitleConverter;
    }

    @Override
    public List<MemberDto> getAll() {
        return memberRepository.findAll()
                .stream().map(entity -> memberConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto save(MemberDto memberDto) {
        Member member = memberConverter.toEntity(memberDto);
        if(member.getDepartment().getId() == null){
            departmentRepository.save(member.getDepartment());
        }
        else{
            Optional<Department> department = departmentRepository.findById(member.getDepartment().getId());
            if(department.isEmpty()){
                departmentRepository.save(member.getDepartment());
            }
        }
        if(member.getAcademicTitle().getId() == null){
            academicTitleRepository.save(member.getAcademicTitle());
        }
        else{
            Optional<AcademicTitle> academicTitle =
                    academicTitleRepository.findById(member.getAcademicTitle().getId());
            if(academicTitle.isEmpty()){
                academicTitleRepository.save(member.getAcademicTitle());
            }
        }
        if(member.getEducationTitle().getId() == null){
            educationTitleRepository.save(member.getEducationTitle());
        }
        else{
            Optional<EducationTitle> educationTitle =
                    educationTitleRepository.findById(member.getEducationTitle().getId());
            if(educationTitle.isEmpty()){
                educationTitleRepository.save(member.getEducationTitle());
            }
        }
        if(member.getScientificField().getId() == null){
            scientificFieldRepository.save(member.getScientificField());
        }
        else{
            Optional<ScientificField> scientificField =
                    scientificFieldRepository.findById(member.getScientificField().getId());
            if(scientificField.isEmpty()){
                scientificFieldRepository.save(member.getScientificField());
            }
        }

        member = memberRepository.save(member);
        return memberConverter.toDto(member);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<Member> mem = memberRepository.findById(id);
        if(mem.isPresent()){
            Member member = mem.get();
            memberRepository.delete(member);
        }
        else{
            throw new EntityNotFoundException(
                    "Member with id = " + id + " does not exist!"
            );
        }
    }

    @Override
    public MemberDto update(MemberDto memberDto) throws EntityNotFoundException {
        Optional<Member> mem = memberRepository.findById(memberDto.getId());
        if(mem.isPresent()){
            Member member = mem.get();
            Department dept = departmentConverter.toEntity(memberDto.getDepartmentDto());
            if(dept.getId() == null){
                departmentRepository.save(dept);
            }
            else{
                Optional<Department> department = departmentRepository.findById(dept.getId());
                if(department.isEmpty()){
                    departmentRepository.save(dept);
                }
            }
            if(member.getAcademicTitle().getId() == null){
                academicTitleRepository.save(member.getAcademicTitle());
            }
            else{
                Optional<AcademicTitle> academicTitle =
                        academicTitleRepository.findById(member.getAcademicTitle().getId());
                if(academicTitle.isEmpty()){
                    academicTitleRepository.save(member.getAcademicTitle());
                }
            }
            if(member.getEducationTitle().getId() == null){
                educationTitleRepository.save(member.getEducationTitle());
            }
            else{
                Optional<EducationTitle> educationTitle =
                        educationTitleRepository.findById(member.getEducationTitle().getId());
                if(educationTitle.isEmpty()){
                    educationTitleRepository.save(member.getEducationTitle());
                }
            }
            if(member.getScientificField().getId() == null){
                scientificFieldRepository.save(member.getScientificField());
            }
            else{
                Optional<ScientificField> scientificField =
                        scientificFieldRepository.findById(member.getScientificField().getId());
                if(scientificField.isEmpty()){
                    scientificFieldRepository.save(member.getScientificField());
                }
            }

            member.setFirstname(memberDto.getFirstname());
            member.setLastname(memberDto.getLastname());
            member.setDepartment(dept);
            member.setAcademicTitle(academicTitleConverter.toEntity(memberDto.getAcademicTitleDto()));
            member.setEducationTitle(educationTitleConverter.toEntity(memberDto.getEducationTitleDto()));
            member.setScientificField(scientificFieldConverter.toEntity(memberDto.getScientificFieldDto()));
            member = memberRepository.save(member);
            return memberConverter.toDto(member);
        }
        else{
            throw new EntityNotFoundException(
                    "Member with id = " + memberDto.getId() + " does not exist!"
            );
        }
    }

    @Override
    public MemberDto findById(Long id) throws EntityNotFoundException {
        Optional<Member> mem = memberRepository.findById(id);
        if(mem.isPresent()){
            Member member = mem.get();
            return memberConverter.toDto(member);
        }
        else{
            throw new EntityNotFoundException(
                    "Member with id = " + id + " does not exist!"
            );
        }
    }
}
