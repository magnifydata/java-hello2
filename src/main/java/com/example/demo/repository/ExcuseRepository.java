package com.example.demo.repository;

import com.example.demo.model.Excuse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcuseRepository extends JpaRepository<Excuse, Long> {
}
