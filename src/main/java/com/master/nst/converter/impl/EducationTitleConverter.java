package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.EducationTitle;
import com.master.nst.dto.EducationTitleDto;
import org.springframework.stereotype.Component;

@Component
public class EducationTitleConverter implements DtoEntityConverter<EducationTitleDto, EducationTitle> {

    @Override
    public EducationTitleDto toDto(EducationTitle entity) {
        return new EducationTitleDto(entity.getId(), entity.getName());
    }

    @Override
    public EducationTitle toEntity(EducationTitleDto dto) {
        return new EducationTitle(dto.getId(), dto.getName());
    }
}
