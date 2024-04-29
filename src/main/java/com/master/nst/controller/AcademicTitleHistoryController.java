package com.master.nst.controller;

import com.master.nst.domain.AcademicTitleHistory;
import com.master.nst.dto.AcademicTitleHistoryDto;
import com.master.nst.service.AcademicTitleHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academic-title-history")
public class AcademicTitleHistoryController {

    private AcademicTitleHistoryService academicTitleHistoryService;

    public AcademicTitleHistoryController(AcademicTitleHistoryService academicTitleHistoryService) {
        this.academicTitleHistoryService = academicTitleHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<AcademicTitleHistoryDto>> getAll(){
        List<AcademicTitleHistoryDto> list = academicTitleHistoryService.getAll();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        academicTitleHistoryService.delete(id);
        return new ResponseEntity<>(
                "Academic title history with id = " +id+ " is removed!", HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicTitleHistoryDto> findById(@PathVariable("id") Long id){
        AcademicTitleHistoryDto academicTitleHistoryDto = academicTitleHistoryService.findById(id);
        return new ResponseEntity<>(academicTitleHistoryDto, HttpStatus.FOUND);

    }
}
