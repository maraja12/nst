package com.master.nst.dto;

import com.master.nst.domain.MemberRole;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class DepartmentHistoryDto implements Serializable {

    private Long id;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    private MemberRole role;
    private DepartmentDto departmentDto;
    private MemberDto memberDto;

    public DepartmentHistoryDto() {
    }

    public DepartmentHistoryDto(Long id, LocalDate startDate, LocalDate endDate,
                                MemberRole role, DepartmentDto departmentDto, MemberDto memberDto) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.role = role;
        this.departmentDto = departmentDto;
        this.memberDto = memberDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public DepartmentDto getDepartmentDto() {
        return departmentDto;
    }

    public void setDepartmentDto(DepartmentDto departmentDto) {
        this.departmentDto = departmentDto;
    }

    public MemberDto getMemberDto() {
        return memberDto;
    }

    public void setMemberDto(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentHistoryDto that = (DepartmentHistoryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && role == that.role && Objects.equals(departmentDto, that.departmentDto) && Objects.equals(memberDto, that.memberDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, role, departmentDto, memberDto);
    }
}
