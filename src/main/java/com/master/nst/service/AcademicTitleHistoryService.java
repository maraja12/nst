package com.master.nst.service;

import com.master.nst.dto.AcademicTitleHistoryDto;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface AcademicTitleHistoryService {

    List<AcademicTitleHistoryDto> getAll();
    void delete(Long id) throws EntityNotFoundException;
    AcademicTitleHistoryDto findById(Long id) throws EntityNotFoundException;
}
