package com.master.nst.service;

import com.master.nst.dto.AcademicTitleDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface AcademicTitleService {

    List<AcademicTitleDto> getAll();
    AcademicTitleDto save(AcademicTitleDto academicTitleDto) throws EntityAlreadyExistsException;
    void delete(Long id) throws EntityNotFoundException;
    AcademicTitleDto update(AcademicTitleDto academicTitleDto) throws EntityNotFoundException;
    AcademicTitleDto findById(Long id) throws EntityNotFoundException;
}
