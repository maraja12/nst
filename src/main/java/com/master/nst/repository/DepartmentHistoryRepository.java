package com.master.nst.repository;

import com.master.nst.domain.Department;
import com.master.nst.domain.DepartmentHistory;
import com.master.nst.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentHistoryRepository extends JpaRepository<DepartmentHistory, Long> {

    Optional<List<DepartmentHistory>> findByRole(MemberRole role);
    Optional<List<DepartmentHistory>> findByDepartment(Department department);
}
