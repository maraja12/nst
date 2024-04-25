package com.master.nst.repository;

import com.master.nst.domain.AcademicTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicTitleRepository extends JpaRepository<AcademicTitle, Long> {

    Optional<AcademicTitle> findByName(String name);
}
