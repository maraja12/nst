package com.master.nst.controller;

import com.master.nst.dto.EducationTitleDto;
import com.master.nst.service.EducationTitleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education-title")
public class EducationTitleController {

    private EducationTitleService educationTitleService;

    public EducationTitleController(EducationTitleService educationTitleService) {
        this.educationTitleService = educationTitleService;
    }

    @GetMapping
    public ResponseEntity<List<EducationTitleDto>> getAll(){
        List<EducationTitleDto> titles = educationTitleService.getAll();
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EducationTitleDto> save(@Valid @RequestBody EducationTitleDto educationTitleDto){
        EducationTitleDto dto = educationTitleService.save(educationTitleDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        educationTitleService.delete(id);
        return new ResponseEntity<>("Education title with id = " + id +
                " is removed!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationTitleDto> update(EducationTitleDto educationTitleDto){
        EducationTitleDto dto = educationTitleService.update(educationTitleDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationTitleDto> findById(@PathVariable("id") Long id){
        EducationTitleDto dto = educationTitleService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }
}

