package com.example.CalcBack2.Repository;

import com.example.CalcBack2.Entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository <Result, Long> {
}
