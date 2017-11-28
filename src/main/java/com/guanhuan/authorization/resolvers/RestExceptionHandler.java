package com.guanhuan.authorization.resolvers;

import java.io.IOException;

import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 不使用这种方式
 *
 */
//@ControllerAdvice
public class RestExceptionHandler{


    //运行时异常
    @ExceptionHandler({RuntimeException.class, NullPointerException.class, ClassCastException.class,
            IOException.class, NoSuchMethodException.class, IndexOutOfBoundsException.class
    })
    @ResponseBody  
    public String runtimeExceptionHandler(RuntimeException runtimeException) {  

        return ResultModel.error(ResultStatus.SYS_ERROR).toString();
    }  


    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class,
            MissingServletRequestParameterException.class})
    @ResponseBody
    public String request400(HttpMessageNotReadableException ex){
        return ResultModel.error(ResultStatus.SYS_ERROR_400).toString();
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public String request405(){
        return ResultModel.error(ResultStatus.SYS_ERROR_405).toString();
    }
    //406错误
    @ExceptionHandler({HttpMediaTypeException.class})
    @ResponseBody
    public String request406(){
        return ResultModel.error(ResultStatus.SYS_ERROR_415).toString();
    }
    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class,HttpMessageNotWritableException.class})
    @ResponseBody
    public String server500(RuntimeException runtimeException){
        return ResultModel.error(ResultStatus.SYS_ERROR_500).toString();
    }
}