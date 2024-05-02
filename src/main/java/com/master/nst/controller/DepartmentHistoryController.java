package com.master.nst.controller;

import com.master.nst.dto.DepartmentHistoryDto;
import com.master.nst.service.DepartmentHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department-history")
public class DepartmentHistoryController {

    private DepartmentHistoryService departmentHistoryService;

    public DepartmentHistoryController(DepartmentHistoryService departmentHistoryService) {
        this.departmentHistoryService = departmentHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentHistoryDto>>getAll(){
        List<DepartmentHistoryDto> histories = departmentHistoryService.getAll();
        return new ResponseEntity<>(histories, HttpStatus.FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        departmentHistoryService.delete(id);
        return new ResponseEntity<>("Department history with id = " + id + " is removed!",
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentHistoryDto> findById(@PathVariable("id")Long id){
        DepartmentHistoryDto departmentHistoryDto = departmentHistoryService.findById(id);
        return new ResponseEntity<>(departmentHistoryDto, HttpStatus.FOUND);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<DepartmentHistoryDto>> findByRole(@PathVariable("role")String role){
        List<DepartmentHistoryDto> departmentHistoryDto = departmentHistoryService.findByMemberRole(role);
        return new ResponseEntity<>(departmentHistoryDto, HttpStatus.FOUND);
    }

    @GetMapping("/dept/{dept-id}")
    public ResponseEntity<List<DepartmentHistoryDto>> findByDepartment(@PathVariable("dept-id")Long id){
        List<DepartmentHistoryDto> departmentHistoryDto = departmentHistoryService.findByDepartmentId(id);
        return new ResponseEntity<>(departmentHistoryDto, HttpStatus.FOUND);
    }

}
