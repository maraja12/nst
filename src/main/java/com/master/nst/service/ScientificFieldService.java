package com.master.nst.service;

import com.master.nst.dto.ScientificFieldDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import java.util.List;


public interface ScientificFieldService {

    ScientificFieldDto save(ScientificFieldDto scientificFieldDto) throws EntityAlreadyExistsException;
    List<ScientificFieldDto> getAll();
    void delete(Long id) throws EntityNotFoundException;
    ScientificFieldDto update(ScientificFieldDto scientificFieldDto) throws EntityNotFoundException;
    ScientificFieldDto findById(Long id) throws EntityNotFoundException;
}
