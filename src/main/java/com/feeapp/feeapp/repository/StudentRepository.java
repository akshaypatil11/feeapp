package com.feeapp.feeapp.repository;

import com.feeapp.feeapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
