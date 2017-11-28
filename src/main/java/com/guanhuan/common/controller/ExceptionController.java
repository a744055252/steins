package com.guanhuan.common.controller;

import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常处理，将异常信息转换为json格式的形式返回
 *
 * @author liguanhuan_a@163.com
 * @create 2017-11-28 17:30
 **/
@RestController
@RequestMapping("/Error")
public class ExceptionController {

    @RequestMapping("404")
    @ResponseBody
    public ResponseEntity<ResultModel> error_404(){
        return ResponseEntity.ok(ResultModel.error(ResultStatus.SYS_ERROR_404));
    }

    @RequestMapping("500")
    @ResponseBody
    public ResponseEntity<ResultModel> error_500(){
        return ResponseEntity.ok(ResultModel.error(ResultStatus.SYS_ERROR_500));
    }

}
