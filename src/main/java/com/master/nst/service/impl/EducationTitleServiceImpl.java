package com.master.nst.service.impl;

import com.master.nst.converter.impl.EducationTitleConverter;
import com.master.nst.domain.EducationTitle;
import com.master.nst.dto.EducationTitleDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import com.master.nst.repository.EducationTitleRepository;
import com.master.nst.service.EducationTitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EducationTitleServiceImpl implements EducationTitleService {

    private EducationTitleRepository educationTitleRepository;
    private EducationTitleConverter educationTitleConverter;

    public EducationTitleServiceImpl(EducationTitleRepository educationTitleRepository,
                                     EducationTitleConverter educationTitleConverter) {
        this.educationTitleRepository = educationTitleRepository;
        this.educationTitleConverter = educationTitleConverter;
    }

    @Override
    public List<EducationTitleDto> getAll() {
        return educationTitleRepository.findAll()
                .stream().map(entity -> educationTitleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public EducationTitleDto save(EducationTitleDto educationTitleDto) throws EntityAlreadyExistsException {
        Optional<EducationTitle> educTitle = educationTitleRepository.findByName(educationTitleDto.getName());
        if(educTitle.isPresent()){
            throw new EntityAlreadyExistsException
                    ("Education title with name = " + educationTitleDto.getName() +
                            " already exists!");
        }
        else{
            EducationTitle educationTitle = educationTitleConverter.toEntity(educationTitleDto);
            educationTitle = educationTitleRepository.save(educationTitle);
            return educationTitleConverter.toDto(educationTitle);
        }
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Optional<EducationTitle> educTitle = educationTitleRepository.findById(id);
        if(educTitle.isPresent()){
            EducationTitle educationTitle = educTitle.get();
            educationTitleRepository.delete(educationTitle);
        }
        else{
            throw new EntityNotFoundException
                    ("Education title with id = " + id + " does not exist!");
        }
    }

    @Override
    public EducationTitleDto update(EducationTitleDto educationTitleDto) throws EntityNotFoundException {
        Optional<EducationTitle> educTitle = educationTitleRepository.findById(educationTitleDto.getId());
        if(educTitle.isPresent()){
            EducationTitle educationTitle = educTitle.get();
            educationTitle.setName(educationTitleDto.getName());
            educationTitle = educationTitleRepository.save(educationTitle);
            return educationTitleConverter.toDto(educationTitle);
        }
        else{
            throw new EntityNotFoundException
                    ("Education title with id = " + educationTitleDto.getId() + " does not exist!");
        }
    }

    @Override
    public EducationTitleDto findById(Long id) throws EntityNotFoundException {
        Optional<EducationTitle> educTitle = educationTitleRepository.findById(id);
        if(educTitle.isPresent()){
            EducationTitle educationTitle = educTitle.get();
            return educationTitleConverter.toDto(educationTitle);
        }
        else{
            throw new EntityNotFoundException
                    ("Education title with id = " + id + " does not exist!");
        }
    }
}
