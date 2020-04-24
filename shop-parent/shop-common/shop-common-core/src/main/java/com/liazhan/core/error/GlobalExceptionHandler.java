package com.liazhan.core.error;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version:V1.0
 * @Description: 全局异常捕捉类
 * @author: Liazhan
 * @date 2020/4/24 11:40
 */
@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseServiceImpl<JSONObject> {

    /**
     * 接口的参数校验异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<JSONObject> methodArgumentsNotValidExceptionHanlder(MethodArgumentNotValidException e){
        //从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        //返回错误信息
        return getResultError(objectError.getDefaultMessage());
    }

    /**
     * 运行时异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<JSONObject> runtimeExceptionHandler(RuntimeException e){
        log.error("###全局捕获运行时异常###,error:{}",e);
        return getResultError("系统错误!");
    }
}
