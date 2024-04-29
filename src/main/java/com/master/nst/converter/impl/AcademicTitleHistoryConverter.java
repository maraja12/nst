package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.AcademicTitleHistory;
import com.master.nst.dto.AcademicTitleHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleHistoryConverter implements DtoEntityConverter<AcademicTitleHistoryDto, AcademicTitleHistory> {

    @Autowired
    private MemberConverter memberConverter;
    @Autowired
    private AcademicTitleConverter academicTitleConverter;

    @Autowired
    private ScientificFieldConverter scientificFieldConverter;

    @Override
    public AcademicTitleHistoryDto toDto(AcademicTitleHistory entity) {
        return new AcademicTitleHistoryDto(
                entity.getId(),
                memberConverter.toDto(entity.getMember()),
                entity.getStartDate(),
                entity.getEndDate(),
                academicTitleConverter.toDto(entity.getAcademicTitle()),
                scientificFieldConverter.toDto(entity.getScientificField())
        );
    }

    @Override
    public AcademicTitleHistory toEntity(AcademicTitleHistoryDto dto) {
        return new AcademicTitleHistory(
                dto.getId(),
                memberConverter.toEntity(dto.getMemberDto()),
                dto.getStartDate(),
                dto.getEndDate(),
                academicTitleConverter.toEntity(dto.getAcademicTitleDto()),
                scientificFieldConverter.toEntity(dto.getScientificFieldDto())
        );
    }
}
