package com.example.CalcBack2.service.impl;

import com.example.CalcBack2.dto.ResultCreateDTO;
import com.example.CalcBack2.dto.ResultDTO;
import com.example.CalcBack2.entity.Result;
import com.example.CalcBack2.repository.ResultRepository;
import com.example.CalcBack2.service.ResultService;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ModelMapper modelMapper;
    private final ResultRepository repository;

    @Override
    public ResultDTO save(ResultCreateDTO resultCreateDTO) {
       resultCreateDTO.setResult(getResult(resultCreateDTO.getInput()));
        Result savedResult = repository.save(modelMapper.map(resultCreateDTO, Result.class));
        return modelMapper.map(savedResult, ResultDTO.class);
    }

    @Override
    public ResultDTO getById(Long id) throws Exception {
        Optional<Result> optionalResult = repository.findById(id);
        if (optionalResult.isEmpty()) {
            throw new Exception(String.format("Not a number", id));
        }
        return modelMapper.map(optionalResult.get(), ResultDTO.class);
    }

    @Override
    public List<ResultDTO> getAll() {
        return repository.findAll().stream()
                .map(result -> modelMapper.map(result, ResultDTO.class))
                .collect(Collectors.toList());
    }


    private String getResult(String input) {
        return String.valueOf(rpnToAnswer(expressionToRPN(input)));
    }

    private String expressionToRPN(String expr) {
        StringBuilder current = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        int priority;
        for(int i = 0; i < expr.length(); i++){
             priority = getPriority(expr.charAt(i));

             if (priority == 0) {
                 current.append(expr.charAt(i));
             }
             if (priority == 1) {
                 stack.push(expr.charAt(i));
             }
             if (priority > 1) {
                 current.append(' ');
                 while (!stack.isEmpty()) {
                     if (getPriority(stack.peek()) >= priority) {
                         current.append(stack.pop());
                     } else break;
                 }
                 stack.push(expr.charAt(i));
             }

             if (priority == -1) {
                 current.append(' ');
                 while(getPriority(stack.peek()) !=1) {
                     current.append(stack.pop());
                 }
                 stack.pop();
             }
        }

        while(!stack.empty()) {
            current.append(stack.pop());
        }

        return current.toString();
    }

    private double rpnToAnswer(String rpm) {
        String operand = new String();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpm.length(); i++) {
            if (rpm.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpm.charAt(i)) == 0) {
                while(rpm.charAt(i) != ' ' && getPriority(rpm.charAt(i)) == 0) {
                    operand += rpm.charAt(i++);
                    if (i == rpm.length()) break;
                }
                stack.push(Double.parseDouble(operand));
                operand = new String();
            }
            if (getPriority(rpm.charAt(i)) > 1) {
                double a =stack.pop();
                double b = stack.pop();

                if (rpm.charAt(i) == '+') {
                    stack.push(b+a);
                }else if (rpm.charAt(i) == '-') {
                    stack.push(b-a);
                }else if (rpm.charAt(i) == '*') {
                    stack.push(b*a);
                }else if (rpm.charAt(i) == '/') {
                    stack.push(b/a);
                }
            }
        }
        return stack.pop();
    }

    private int getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        }else if(token == '+' || token == '-') {
            return 2;
        }else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else return 0;
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}








