package com.master.nst.repository;

import com.master.nst.domain.AcademicTitle;
import com.master.nst.domain.AcademicTitleHistory;
import com.master.nst.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicTitleHistoryRepository extends JpaRepository<AcademicTitleHistory, Long> {

    Optional<AcademicTitleHistory> findByMemberAndAcademicTitle (Member member, AcademicTitle academicTitle);
}
