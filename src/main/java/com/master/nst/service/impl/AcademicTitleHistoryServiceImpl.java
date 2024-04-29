package com.master.nst.service.impl;

import com.master.nst.converter.impl.AcademicTitleHistoryConverter;
import com.master.nst.domain.AcademicTitleHistory;
import com.master.nst.dto.AcademicTitleHistoryDto;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.AcademicTitleHistoryRepository;
import com.master.nst.service.AcademicTitleHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicTitleHistoryServiceImpl implements AcademicTitleHistoryService {

    private AcademicTitleHistoryRepository academicTitleHistoryRepository;
    private AcademicTitleHistoryConverter academicTitleHistoryConverter;

    public AcademicTitleHistoryServiceImpl(AcademicTitleHistoryRepository academicTitleHistoryRepository,
                                           AcademicTitleHistoryConverter academicTitleHistoryConverter) {
        this.academicTitleHistoryRepository = academicTitleHistoryRepository;
        this.academicTitleHistoryConverter = academicTitleHistoryConverter;
    }

    @Override
    public List<AcademicTitleHistoryDto> getAll() {
        return academicTitleHistoryRepository.findAll()
                .stream().map(entity -> academicTitleHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<AcademicTitleHistory> atHistory = academicTitleHistoryRepository.findById(id);
        if(atHistory.isPresent()){
            AcademicTitleHistory academicTitleHistory = atHistory.get();
           academicTitleHistoryRepository.delete(academicTitleHistory);
        }
        else{
            throw new EntityNotFoundException(
                    "Academic title history with id = " + id + " does not exist!"
            );
        }
    }

    @Override
    public AcademicTitleHistoryDto findById(Long id) throws EntityNotFoundException {
        Optional<AcademicTitleHistory> atHistory = academicTitleHistoryRepository.findById(id);
        if(atHistory.isPresent()){
            AcademicTitleHistory academicTitleHistory = atHistory.get();
            return academicTitleHistoryConverter.toDto(academicTitleHistory);
        }
        else{
            throw new EntityNotFoundException(
                    "Academic title history with id = " + id + " does not exist!"
            );
        }
    }

}
