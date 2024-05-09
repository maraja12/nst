package com.master.nst.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Member's firstname is required!")
    @Column(name = "firstname")
    private String firstname;
    @NotEmpty(message = "Member's lastname is required!")
    @Column(name = "lastname")
    private String lastname;
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne()
    @JoinColumn(name = "academic_title_id")
    private AcademicTitle academicTitle;
    @ManyToOne()
    @JoinColumn(name = "education_title_id")
    private EducationTitle educationTitle;

    @ManyToOne()
    @JoinColumn(name = "scientific_field_id")
    private ScientificField scientificField;

//    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<AcademicTitleHistory> histories;

    public Member() {
    }

    public Member(Long id, String firstname, String lastname,
                  Department department,
                  AcademicTitle academicTitle,
                  EducationTitle educationTitle,
                  ScientificField scientificField) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.academicTitle = academicTitle;
        this.educationTitle = educationTitle;
        this.scientificField = scientificField;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public EducationTitle getEducationTitle() {
        return educationTitle;
    }

    public void setEducationTitle(EducationTitle educationTitle) {
        this.educationTitle = educationTitle;
    }

    public ScientificField getScientificField() {
        return scientificField;
    }

    public void setScientificField(ScientificField scientificField) {
        this.scientificField = scientificField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(firstname, member.firstname) && Objects.equals(lastname, member.lastname) && Objects.equals(department, member.department) && Objects.equals(academicTitle, member.academicTitle) && Objects.equals(educationTitle, member.educationTitle) && Objects.equals(scientificField, member.scientificField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, department, academicTitle, educationTitle, scientificField);
    }
}
