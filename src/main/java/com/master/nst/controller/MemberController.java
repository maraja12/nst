package com.master.nst.controller;

import com.master.nst.dto.MemberDto;
import com.master.nst.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAll(){
        List<MemberDto> members = memberService.getAll();
        return new ResponseEntity<>(members, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<MemberDto> save(@Valid @RequestBody MemberDto memberDto){
        MemberDto dto = memberService.save(memberDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        memberService.delete(id);
        return new ResponseEntity<>("Member with id = " + id +
                " is removed!", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MemberDto> update(@Valid @RequestBody MemberDto memberDto){
        MemberDto dto = memberService.update(memberDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> findById(@PathVariable("id") Long id){
        MemberDto dto = memberService.findById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
