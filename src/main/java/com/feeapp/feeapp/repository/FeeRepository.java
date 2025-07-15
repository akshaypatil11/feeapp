package com.feeapp.feeapp.repository;

import com.feeapp.feeapp.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee, Long> {
}
