package com.master.nst.service.impl;

import com.master.nst.converter.impl.AcademicTitleConverter;
import com.master.nst.domain.AcademicTitle;
import com.master.nst.domain.Department;
import com.master.nst.dto.AcademicTitleDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.AcademicTitleRepository;
import com.master.nst.service.AcademicTitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicTitleServiceImpl implements AcademicTitleService {

    private AcademicTitleRepository academicTitleRepository;
    private AcademicTitleConverter academicTitleConverter;

    public AcademicTitleServiceImpl(AcademicTitleRepository academicTitleRepository,
                                    AcademicTitleConverter academicTitleConverter) {
        this.academicTitleRepository = academicTitleRepository;
        this.academicTitleConverter = academicTitleConverter;
    }

    @Override
    public List<AcademicTitleDto> getAll() {
        return academicTitleRepository.findAll()
                .stream().map(entity -> academicTitleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public AcademicTitleDto save(AcademicTitleDto academicTitleDto) throws EntityAlreadyExistsException {
        Optional<AcademicTitle> acadTitle =
                academicTitleRepository.findByName(academicTitleDto.getName());
        if(acadTitle.isPresent()){
            throw new EntityAlreadyExistsException(
                    "Department with name = " + academicTitleDto.getName() +
                    " already exists!");
        }
        else{
            AcademicTitle academicTitle = academicTitleConverter.toEntity(academicTitleDto);
            academicTitle = academicTitleRepository.save(academicTitle);
            return academicTitleConverter.toDto(academicTitle);
        }
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<AcademicTitle> acadTitle = academicTitleRepository.findById(id);
        if(acadTitle.isPresent()){
            AcademicTitle academicTitle = acadTitle.get();
            academicTitleRepository.delete(academicTitle);
        }
        else{
            throw new EntityNotFoundException("Academic title with id = " + id +
                    " does not exist!");
        }
    }

    @Override
    public AcademicTitleDto update(AcademicTitleDto academicTitleDto) throws EntityNotFoundException {
        Optional<AcademicTitle> acadTitle = academicTitleRepository.findById(academicTitleDto.getId());
        if(acadTitle.isPresent()){
            AcademicTitle academicTitle = acadTitle.get();
            academicTitle.setName(academicTitleDto.getName());
            academicTitle = academicTitleRepository.save(academicTitle);
            return academicTitleConverter.toDto(academicTitle);
        }
        else{
            throw new EntityNotFoundException("Academic title with id = " + academicTitleDto.getId() +
                    " does not exist!");
        }
    }

    @Override
    public AcademicTitleDto findById(Long id) throws EntityNotFoundException {
        Optional<AcademicTitle> acadTitle = academicTitleRepository.findById(id);
        if(acadTitle.isPresent()){
            AcademicTitle academicTitle = acadTitle.get();
            return academicTitleConverter.toDto(academicTitle);
        }
        else{
            throw new EntityNotFoundException("Academic title with id = " + id +
                    " does not exist!");
        }
    }
}
