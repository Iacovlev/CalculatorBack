package com.example.CalcBack2.service;

import com.example.CalcBack2.dto.ResultCreateDTO;
import com.example.CalcBack2.dto.ResultDTO;


public interface ResultService {
    ResultDTO save(ResultCreateDTO resultDTO) throws Exception;

    ResultDTO getById(Long id) throws Exception;
}
