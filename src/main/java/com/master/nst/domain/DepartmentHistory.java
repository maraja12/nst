package com.master.nst.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "department_history")
public class DepartmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Start date is required!")
    @PastOrPresent
    @Column(name = "start_date")
    private LocalDate startDate;
    @FutureOrPresent
    @Column(name = "end_date")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;

    public DepartmentHistory() {
    }

    public DepartmentHistory(Long id, LocalDate startDate, LocalDate endDate,
                             MemberRole role, Department department, Member member) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.role = role;
        this.department = department;
        this.member = member;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentHistory that = (DepartmentHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && role == that.role && Objects.equals(department, that.department) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, role, department, member);
    }
}
