package com.example.CalcBack2.Service;

import com.example.CalcBack2.DTO.ResultCreateDTO;
import com.example.CalcBack2.DTO.ResultDTO;

import java.util.List;


public interface ResultService {
    ResultDTO save(ResultCreateDTO resultDTO) throws Exception;

    ResultDTO getById(Long id) throws Exception;
}
