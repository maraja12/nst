package com.master.nst.repository;

import com.master.nst.domain.ScientificField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScientificFieldRepository extends JpaRepository<ScientificField, Long> {

    Optional<ScientificField> findByName(String name);
}
