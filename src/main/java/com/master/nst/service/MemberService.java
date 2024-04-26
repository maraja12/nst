package com.master.nst.service;

import com.master.nst.dto.MemberDto;
import com.master.nst.exception.EntityNotFoundException;

import java.util.List;

public interface MemberService {

    List<MemberDto> getAll();
    MemberDto save(MemberDto memberDto);
    void delete(Long id) throws EntityNotFoundException;
    MemberDto update(MemberDto memberDto) throws EntityNotFoundException;
    MemberDto findById(Long id) throws EntityNotFoundException;
}
