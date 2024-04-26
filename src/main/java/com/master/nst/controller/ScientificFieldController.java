package com.master.nst.controller;

import com.master.nst.dto.ScientificFieldDto;
import com.master.nst.service.ScientificFieldService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scientific-field")
public class ScientificFieldController {

    private ScientificFieldService scientificFieldService;

    public ScientificFieldController(ScientificFieldService scientificFieldService) {
        this.scientificFieldService = scientificFieldService;
    }

    @GetMapping
    public ResponseEntity<List<ScientificFieldDto>> getAll(){
        List<ScientificFieldDto> fields = scientificFieldService.getAll();
        return new ResponseEntity<>(fields, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<ScientificFieldDto> save(@Valid @RequestBody ScientificFieldDto scientificFieldDto){
        ScientificFieldDto dto = scientificFieldService.save(scientificFieldDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        scientificFieldService.delete(id);
        return new ResponseEntity<>("Scientific field with id = " + id +
                " is deleted!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScientificFieldDto> update(@Valid @RequestBody ScientificFieldDto scientificFieldDto){
        ScientificFieldDto dto = scientificFieldService.update(scientificFieldDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScientificFieldDto> findById(@PathVariable("id") Long id){
        ScientificFieldDto scientificFieldDto = scientificFieldService.findById(id);
        return new ResponseEntity<>(scientificFieldDto, HttpStatus.OK);
    }
}
