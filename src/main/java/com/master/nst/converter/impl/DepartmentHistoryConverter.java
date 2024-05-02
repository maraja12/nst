package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.DepartmentHistory;
import com.master.nst.dto.DepartmentHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentHistoryConverter implements DtoEntityConverter<DepartmentHistoryDto, DepartmentHistory> {

    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private MemberConverter memberConverter;
    @Override
    public DepartmentHistoryDto toDto(DepartmentHistory entity) {
        return new DepartmentHistoryDto(
                entity.getId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getRole(),
                departmentConverter.toDto(entity.getDepartment()),
                memberConverter.toDto(entity.getMember()));
    }

    @Override
    public DepartmentHistory toEntity(DepartmentHistoryDto dto) {
        return new DepartmentHistory(
                dto.getId(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getRole(),
                departmentConverter.toEntity(dto.getDepartmentDto()),
                memberConverter.toEntity(dto.getMemberDto())
        );
    }
}
