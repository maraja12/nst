package com.master.nst.service;

import com.master.nst.dto.EducationTitleDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface EducationTitleService {

    List<EducationTitleDto> getAll();
    EducationTitleDto save(EducationTitleDto educationTitleDto) throws EntityAlreadyExistsException;
    void delete(Long id) throws EntityNotFoundException;
    EducationTitleDto update(EducationTitleDto educationTitleDto) throws EntityNotFoundException;
    EducationTitleDto findById(Long id) throws EntityNotFoundException;
}
