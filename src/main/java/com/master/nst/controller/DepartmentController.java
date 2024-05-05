package com.master.nst.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import com.master.nst.dto.DepartmentDto;
        import jakarta.validation.Valid;
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
        DepartmentDto deptDto = departmentService.save(departmentDto);
        return new ResponseEntity<>(deptDto, HttpStatus.CREATED);
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
        departmentService.delete(id);
        return new ResponseEntity<>("Department is removed!", HttpStatus.OK);
    }

    //update department
    @PutMapping
    public ResponseEntity<DepartmentDto> update(@Valid @RequestBody DepartmentDto departmentDto){
        DepartmentDto deptDto = departmentService.update(departmentDto);
        return new ResponseEntity<>(deptDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> findById(@PathVariable("id") Long id){
        DepartmentDto deptDto = departmentService.findById(id);
        return new ResponseEntity<>(deptDto, HttpStatus.OK);
    }

//    @PostMapping("/{dept-manager}")
//    public ResponseEntity<String> setManager(
//            @PathVariable("dept-manager") String deptManger){
//        String[] ids = deptManger.split(",");
//        Long deptId = Long.parseLong(ids[0]);
//        Long managerId = Long.parseLong(ids[1]);
//        departmentService.setManager(deptId, managerId);
//        return new ResponseEntity<>("Manager of department with id = " +deptId+" is saved!",
//                HttpStatus.OK);
//    }

    @PostMapping("/{dept-id}/manager/{member-id}")
    public ResponseEntity<String> setManager(
            @PathVariable("dept-id")Long deptId,
            @PathVariable("member-id")Long memberId){
        departmentService.setManager(deptId, memberId);
        return new ResponseEntity<>("Manager of department with id = " +deptId+" is saved!",
                HttpStatus.OK);
    }

    @PostMapping("/{dept-id}/secretary/{member-id}")
    public ResponseEntity<String> setSecretary(
            @PathVariable("dept-id")Long deptId,
            @PathVariable("member-id")Long memberId){
        departmentService.setSecretary(deptId, memberId);
        return new ResponseEntity<>("Secretary of department with id = " +deptId+" is saved!",
                HttpStatus.OK);
    }
}
