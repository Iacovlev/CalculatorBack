package com.example.CalcBack2.Controller;

import com.example.CalcBack2.DTO.ResultCreateDTO;
import com.example.CalcBack2.DTO.ResultDTO;
import com.example.CalcBack2.Service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service/result")
@RequiredArgsConstructor
public class ResultControllers {
    private final ResultService resultService;

    @PostMapping("/")
    public ResultDTO save(@RequestBody ResultCreateDTO resultDTO) throws Exception {
        return resultService.save(resultDTO);
    }

    @GetMapping("/{id}")
    public ResultDTO getByID(@PathVariable Long id) throws Exception {
        return resultService.getById(id);
    }

}