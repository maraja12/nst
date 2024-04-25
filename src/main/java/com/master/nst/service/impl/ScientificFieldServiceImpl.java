package com.master.nst.service.impl;

import com.master.nst.converter.impl.ScientificFieldConverter;
import com.master.nst.domain.ScientificField;
import com.master.nst.dto.ScientificFieldDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.ScientificFieldRepository;
import com.master.nst.service.ScientificFieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {

    private ScientificFieldRepository scientificFieldRepository;
    private ScientificFieldConverter scientificFieldConverter;

    public ScientificFieldServiceImpl(ScientificFieldRepository scientificFieldRepository,
                                      ScientificFieldConverter scientificFieldConverter) {
        this.scientificFieldRepository = scientificFieldRepository;
        this.scientificFieldConverter = scientificFieldConverter;
    }

    @Override
    public ScientificFieldDto save(ScientificFieldDto scientificFieldDto) throws EntityAlreadyExistsException {
        Optional<ScientificField> scField = scientificFieldRepository.findByName(scientificFieldDto.getName());
        if(scField.isPresent()){
            throw new EntityAlreadyExistsException(
                    "Scientific field with name " + scientificFieldDto.getName() +
                            " already exists!"
            );
        }
        else{
            ScientificField scientificField = scientificFieldConverter.toEntity(scientificFieldDto);
            scientificField = scientificFieldRepository.save(scientificField);
            return scientificFieldConverter.toDto(scientificField);
        }
    }

    @Override
    public List<ScientificFieldDto> getAll() {
        return scientificFieldRepository.findAll()
                .stream().map(entity -> scientificFieldConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<ScientificField> scField = scientificFieldRepository.findById(id);
        if(scField.isPresent()){
            ScientificField scientificField = scField.get();
            scientificFieldRepository.delete(scientificField);
        }
        else{
            throw new EntityNotFoundException(
                    "Scientific field with id = " + id + " does not exist!"
            );
        }
    }

    @Override
    public ScientificFieldDto update(ScientificFieldDto scientificFieldDto) throws EntityNotFoundException {
        Optional<ScientificField> scField = scientificFieldRepository.findById(scientificFieldDto.getId());
        if(scField.isPresent()){
            ScientificField scientificField = scField.get();
            scientificField.setName(scientificFieldDto.getName());
            scientificField = scientificFieldRepository.save(scientificField);
            return scientificFieldConverter.toDto(scientificField);
        }
        else{
            throw new EntityNotFoundException(
                    "Scientific field with id = " + scientificFieldDto.getId() + " does not exist!"
            );
        }
    }

    @Override
    public ScientificFieldDto findById(Long id) throws EntityNotFoundException {
        Optional<ScientificField> scField = scientificFieldRepository.findById(id);
        if(scField.isPresent()){
            ScientificField scientificField = scField.get();
            return scientificFieldConverter.toDto(scientificField);
        }
        else{
            throw new EntityNotFoundException(
                    "Scientific field with id = " + id + " does not exist!"
            );
        }
    }
}
