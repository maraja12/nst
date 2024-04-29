package com.master.nst.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class AcademicTitleHistoryDto implements Serializable {

    private Long id;
    private MemberDto memberDto;
    private LocalDate startDate;
    private LocalDate endDate;
    private AcademicTitleDto academicTitleDto;
    private ScientificFieldDto scientificFieldDto;

    public AcademicTitleHistoryDto() {
    }

    public AcademicTitleHistoryDto(Long id, MemberDto memberDto, LocalDate startDate, LocalDate endDate,
                                   AcademicTitleDto academicTitleDto, ScientificFieldDto scientificFieldDto) {
        this.id = id;
        this.memberDto = memberDto;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicTitleDto = academicTitleDto;
        this.scientificFieldDto = scientificFieldDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemberDto getMemberDto() {
        return memberDto;
    }

    public void setMemberDto(MemberDto memberDto) {
        this.memberDto = memberDto;
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

    public AcademicTitleDto getAcademicTitleDto() {
        return academicTitleDto;
    }

    public void setAcademicTitleDto(AcademicTitleDto academicTitleDto) {
        this.academicTitleDto = academicTitleDto;
    }

    public ScientificFieldDto getScientificFieldDto() {
        return scientificFieldDto;
    }

    public void setScientificFieldDto(ScientificFieldDto scientificFieldDto) {
        this.scientificFieldDto = scientificFieldDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicTitleHistoryDto that = (AcademicTitleHistoryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(memberDto, that.memberDto) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(academicTitleDto, that.academicTitleDto) && Objects.equals(scientificFieldDto, that.scientificFieldDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberDto, startDate, endDate, academicTitleDto, scientificFieldDto);
    }
}
