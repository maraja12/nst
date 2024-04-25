package com.master.nst.converter.impl;

import com.master.nst.converter.DtoEntityConverter;
import com.master.nst.domain.ScientificField;
import com.master.nst.dto.ScientificFieldDto;
import org.springframework.stereotype.Component;

@Component
public class ScientificFieldConverter implements DtoEntityConverter<ScientificFieldDto, ScientificField> {
    @Override
    public ScientificFieldDto toDto(ScientificField entity) {
        return new ScientificFieldDto(entity.getId(), entity.getName());
    }

    @Override
    public ScientificField toEntity(ScientificFieldDto dto) {
        return new ScientificField(dto.getId(), dto.getName());
    }
}
