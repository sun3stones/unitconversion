package com.lei.unitconversion.filter;

import java.util.List;


import com.alibaba.fastjson.JSONObject;
import com.lei.unitconversion.common.ResultCode;
import com.lei.unitconversion.common.UnitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice(basePackages = "com.lei.unitconversion.controller")
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public JSONObject errorResult(HttpServletRequest request, Exception e) {
        UnitResult unitResult = new UnitResult();
        if (e instanceof BindException){
            BindException ex = (BindException)e;
            BindingResult bindingResult = ex.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError allError : allErrors) {
                sb.append(allError.getDefaultMessage()).append(";");
            }
            unitResult.setCode(ResultCode.Param.getCode());
            unitResult.setMsg(sb.insert(0,ResultCode.Param.getMsg()+":").toString());
        }else{
            unitResult.setCode(ResultCode.Program.getCode());
            unitResult.setMsg(ResultCode.Program.getMsg()+":"+e.getMessage());
        }
        LOGGER.debug(e.getMessage());
        return unitResult.toJson();
    }

}