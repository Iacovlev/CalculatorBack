package com.example.CalcBack2.repository;

import com.example.CalcBack2.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository <Result, Long> {
}
