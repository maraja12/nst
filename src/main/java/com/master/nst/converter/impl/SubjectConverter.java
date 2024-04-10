package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.Subject;
import com.master.nst.dto.SubjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter implements DtoEntityConverter<SubjectDto, Subject> {

    @Autowired
    private DepartmentConverter departmentConverter;
    @Override
    public SubjectDto toDto(Subject entity) {
        return new SubjectDto(
                entity.getId(),
                entity.getName(),
                entity.getEspb(),
                departmentConverter.toDto(entity.getDepartment())
        );
    }

    @Override
    public Subject toEntity(SubjectDto dto) {
        return new Subject(
                dto.getId(),
                dto.getName(),
                dto.getEspb(),
                departmentConverter.toEntity(dto.getDepartmentDto())
        );
    }
}
