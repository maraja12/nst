package com.master.nst.repository;

import com.master.nst.domain.EducationTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationTitleRepository extends JpaRepository<EducationTitle, Long> {

    Optional<EducationTitle> findByName(String name);
}
