package com.master.nst.controller;

import com.master.nst.dto.AcademicTitleDto;
import com.master.nst.service.AcademicTitleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/academic-title")
public class AcademicTitleController {

    private AcademicTitleService academicTitleService;

    public AcademicTitleController(AcademicTitleService academicTitleService) {
        this.academicTitleService = academicTitleService;
    }

    @PostMapping
    public ResponseEntity<AcademicTitleDto> save(@Valid @RequestBody AcademicTitleDto dto){
        AcademicTitleDto academicTitleDto = academicTitleService.save(dto);
        return new ResponseEntity<>(academicTitleDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AcademicTitleDto>> getAll(){
        List<AcademicTitleDto> titles = academicTitleService.getAll();
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        academicTitleService.delete(id);
        return new ResponseEntity<>("Academic title with id = " + id + " is removed!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicTitleDto> update(@Valid @RequestBody AcademicTitleDto dto){
        AcademicTitleDto academicTitleDto = academicTitleService.update(dto);
        return new ResponseEntity<>(academicTitleDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicTitleDto> findById(@PathVariable("id") Long id){
        AcademicTitleDto academicTitleDto = academicTitleService.findById(id);
        return new ResponseEntity<>(academicTitleDto, HttpStatus.OK);
    }
}
