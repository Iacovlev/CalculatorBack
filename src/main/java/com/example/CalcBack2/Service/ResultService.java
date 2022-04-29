package com.example.CalcBack2.Service;

import com.example.CalcBack2.DTO.ResultCreateDTO;
import com.example.CalcBack2.DTO.ResultDTO;

import java.util.List;


public interface ResultService {
    ResultDTO save(ResultCreateDTO resultDTO) throws Exception;

    ResultDTO update(ResultDTO resultDTO);

    boolean delete(Long resultId);

    List<ResultDTO> getAll();

    ResultDTO getById(Long id) throws Exception;

}
