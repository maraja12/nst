package com.master.nst.controller;

import com.master.nst.dto.SubjectDto;
import com.master.nst.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //add subject
    @PostMapping
    ResponseEntity<SubjectDto> save(@Valid @RequestBody SubjectDto subjectDto) {
        SubjectDto subjDto = subjectService.save(subjectDto);
        return new ResponseEntity<>(subjDto, HttpStatus.CREATED);
    }

    //get all subjects
    @GetMapping
    ResponseEntity<List<SubjectDto>> getAll(){
        List<SubjectDto> subjects = subjectService.getAll();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    //delete subject
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(Long id){
        subjectService.delete(id);
        return new ResponseEntity<> ("Subject is removed!", HttpStatus.OK);
    }

    //update subject
    @PutMapping
    public ResponseEntity<SubjectDto> update(@Valid @RequestBody SubjectDto subjectDto){
        SubjectDto subjDto = subjectService.update(subjectDto);
        return new ResponseEntity<>(subjDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> findById(@PathVariable("id") Long id){
        SubjectDto subjDto = subjectService.findById(id);
        return new ResponseEntity<>(subjDto, HttpStatus.OK);
    }

}
