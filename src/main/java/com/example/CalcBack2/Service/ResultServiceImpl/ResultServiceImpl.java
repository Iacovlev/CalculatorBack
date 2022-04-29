package com.example.CalcBack2.Service.ResultServiceImpl;

import com.example.CalcBack2.DTO.ResultCreateDTO;
import com.example.CalcBack2.DTO.ResultDTO;
import com.example.CalcBack2.Entity.Result;
import com.example.CalcBack2.Repository.ResultRepository;
import com.example.CalcBack2.Service.ResultService;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import javax.script.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {
    private final ModelMapper modelMapper;
    private final ResultRepository repository;



    @Override
    public ResultDTO
    save(ResultCreateDTO resultCreateDTO) {
       resultCreateDTO.setResult(getResult(resultCreateDTO.getInput()));
        Result savedResult = repository.save(modelMapper.map(resultCreateDTO, Result.class));
        return modelMapper.map(savedResult, ResultDTO.class);
    }

    @Override
    public ResultDTO update(ResultDTO resultDTO) {
        return null;
    }

    @Override
    public boolean delete(Long resultId) {
        return false;
    }

    @Override
    public List<ResultDTO> getAll() {
        return null;
    }

    @Override
    public ResultDTO getById(Long id) throws Exception {
        Optional<Result> optionalResult = repository.findById(id);
        if (optionalResult.isEmpty()) {
            throw new Exception(String.format("Profile with id %s not found", id));
        }
        return modelMapper.map(optionalResult.get(), ResultDTO.class);
    }



    public String getResult(String input) {
        String exp = input;

        String a = ExpressionToRPN(exp);

        String result = String.valueOf(RpnToAnswer(a));

        return result;
    }
    public static String ExpressionToRPN(String Expr) {
        String current = "";
        Stack<Character> stack = new Stack<>();

        int priority;
        for(int i = 0; i < Expr.length(); i++){
         priority = getP(Expr.charAt(i));

         if (priority == 0) {
             current += Expr.charAt(i);
         }
         if (priority == 1) {
             stack.push(Expr.charAt(i));
         }
         if (priority > 1) {
             current += ' ';
             while (!stack.isEmpty()) {
                 if (getP(stack.peek()) >= priority) {
                     current += stack.pop();
                 } else break;
             }
             stack.push(Expr.charAt(i));
         }

         if (priority == -1) {
             current += ' ';
             while(getP(stack.peek()) !=1) {
                 current+=stack.pop();
             }
             stack.pop();
         }

        }

        while(!stack.empty()) {
            current += stack.pop();
        }

        return current;
    }

    public static double RpnToAnswer (String rpm) {
        String operand = new String();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpm.length(); i++) {
            if (rpm.charAt(i) == ' ') {
                continue;
            }
            if (getP(rpm.charAt(i)) == 0) {
                while(rpm.charAt(i) != ' ' && getP(rpm.charAt(i)) == 0) {
                    operand += rpm.charAt(i++);
                    if (i == rpm.length()) break;
                }
                stack.push(Double.parseDouble(operand));
                operand = new String();
            }
            if (getP(rpm.charAt(i)) > 1) {
                double a =stack.pop();
                double b = stack.pop();

                if (rpm.charAt(i) == '+') {
                    stack.push(b+a);
                }
                if (rpm.charAt(i) == '-') {
                    stack.push(b-a);
                }
                if (rpm.charAt(i) == '*') {
                    stack.push(b*a);
                }
                if (rpm.charAt(i) == '/') {
                    stack.push(b/a);
                }
            }
        }
        return stack.pop();
    }

    private static int getP(char token) {
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

}








