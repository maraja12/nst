package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.Member;
import com.master.nst.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements DtoEntityConverter<MemberDto, Member> {

    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private AcademicTitleConverter academicTitleConverter;
    @Autowired
    private EducationTitleConverter educationTitleConverter;
    @Autowired
    private ScientificFieldConverter scientificFieldConverter;

    @Override
    public MemberDto toDto(Member entity) {
        return new MemberDto(
                entity.getId(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getRole(),
                departmentConverter.toDto(entity.getDepartment()),
                academicTitleConverter.toDto(entity.getAcademicTitle()),
                educationTitleConverter.toDto(entity.getEducationTitle()),
                scientificFieldConverter.toDto(entity.getScientificField())
                );
    }

    @Override
    public Member toEntity(MemberDto dto) {
        return new Member(
                dto.getId(),
                dto.getFirstname(),
                dto.getLastname(),
                dto.getRole(),
                departmentConverter.toEntity(dto.getDepartmentDto()),
                academicTitleConverter.toEntity(dto.getAcademicTitleDto()),
                educationTitleConverter.toEntity(dto.getEducationTitleDto()),
                scientificFieldConverter.toEntity(dto.getScientificFieldDto())
        );
    }
}
