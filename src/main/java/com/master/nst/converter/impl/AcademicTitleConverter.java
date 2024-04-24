package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.AcademicTitle;
import org.springframework.stereotype.Component;
import com.master.nst.dto.AcademicTitleDto;

@Component
public class AcademicTitleConverter implements DtoEntityConverter<AcademicTitleDto, AcademicTitle> {

    @Override
    public AcademicTitleDto toDto(AcademicTitle entity) {
        return new AcademicTitleDto(entity.getId(), entity.getName());
    }

    @Override
    public AcademicTitle toEntity(AcademicTitleDto dto) {
        return new AcademicTitle(dto.getId(), dto.getName());
    }
}
