package com.master.nst.controller;

import com.master.nst.domain.Department;
//import org.springframework.beans.factory.annotation.Autowired;
import com.master.nst.dto.DepartmentDto;
import com.master.nst.exception.EntityAlreadyExistsException;
import com.master.nst.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.master.nst.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
        System.out.println("Controller is created!");
    }

    //add new department
    @PostMapping
    public ResponseEntity<DepartmentDto> save (@Valid @RequestBody DepartmentDto departmentDto){
        try{
            DepartmentDto deptDto = departmentService.save(departmentDto);
            return new ResponseEntity<>(deptDto, HttpStatus.CREATED);
        }
        catch(EntityAlreadyExistsException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //get all departments
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll(){
        List<DepartmentDto> departments = departmentService.getAll();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    //delete department
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        try {
            departmentService.delete(id);
            return new ResponseEntity<>("Department is removed!", HttpStatus.OK);
        }
        catch(EntityNotFoundException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //update department
    @PutMapping
    public ResponseEntity<DepartmentDto> update(@Valid @RequestBody DepartmentDto departmentDto){
        try{
            DepartmentDto deptDto = departmentService.update(departmentDto);
            return new ResponseEntity<>(deptDto, HttpStatus.OK);
        }
        catch(EntityNotFoundException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> findById(@PathVariable("id") Long id){
        try{
            DepartmentDto deptDto = departmentService.findById(id);
            return new ResponseEntity<>(deptDto, HttpStatus.OK);
        }
        catch(EntityNotFoundException ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
