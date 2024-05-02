package com.master.nst.dto;

import com.master.nst.domain.MemberRole;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class MemberDto implements Serializable {

    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;

    private MemberRole role;
    private DepartmentDto departmentDto;
    private AcademicTitleDto academicTitleDto;
    private EducationTitleDto educationTitleDto;
    private ScientificFieldDto scientificFieldDto;

    public MemberDto() {
    }

    public MemberDto(Long id, String firstname, String lastname, MemberRole role,
                     DepartmentDto departmentDto,
                     AcademicTitleDto academicTitleDto, EducationTitleDto educationTitleDto,
                     ScientificFieldDto scientificFieldDto) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.departmentDto = departmentDto;
        this.academicTitleDto = academicTitleDto;
        this.educationTitleDto = educationTitleDto;
        this.scientificFieldDto = scientificFieldDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public DepartmentDto getDepartmentDto() {
        return departmentDto;
    }

    public void setDepartmentDto(DepartmentDto departmentDto) {
        this.departmentDto = departmentDto;
    }

    public AcademicTitleDto getAcademicTitleDto() {
        return academicTitleDto;
    }

    public void setAcademicTitleDto(AcademicTitleDto academicTitleDto) {
        this.academicTitleDto = academicTitleDto;
    }

    public EducationTitleDto getEducationTitleDto() {
        return educationTitleDto;
    }

    public void setEducationTitleDto(EducationTitleDto educationTitleDto) {
        this.educationTitleDto = educationTitleDto;
    }

    public ScientificFieldDto getScientificFieldDto() {
        return scientificFieldDto;
    }

    public void setScientificFieldDto(ScientificFieldDto scientificFieldDto) {
        this.scientificFieldDto = scientificFieldDto;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDto memberDto = (MemberDto) o;
        return Objects.equals(id, memberDto.id) && Objects.equals(firstname, memberDto.firstname) && Objects.equals(lastname, memberDto.lastname) && role == memberDto.role && Objects.equals(departmentDto, memberDto.departmentDto) && Objects.equals(academicTitleDto, memberDto.academicTitleDto) && Objects.equals(educationTitleDto, memberDto.educationTitleDto) && Objects.equals(scientificFieldDto, memberDto.scientificFieldDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, role, departmentDto, academicTitleDto, educationTitleDto, scientificFieldDto);
    }
}
