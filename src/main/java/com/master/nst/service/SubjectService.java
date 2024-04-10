package com.master.nst.service;

import com.master.nst.dto.SubjectDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface SubjectService {

    List<SubjectDto> getAll();
    SubjectDto save(SubjectDto subjectDto) throws EntityAlreadyExistsException;
    void delete(Long id) throws EntityNotFoundException;
    SubjectDto update(SubjectDto subjectDto) throws EntityNotFoundException;
    SubjectDto findById(Long id) throws EntityNotFoundException;

}
