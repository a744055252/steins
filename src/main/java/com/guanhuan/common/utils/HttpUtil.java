package com.guanhuan.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sun.misc.CharacterEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liguanhuan_a@163.com
 * @create 2017-11-28 15:34
 **/
public class HttpUtil {

    /**
     * 往response中添加错误信息ResultModel
     * @Date: 15:50 2017/11/28
     * @param response
     * @param code
     * @param errorMessage
     */
    public static void returnErrorMessage(HttpServletResponse response, int code,
                                          String errorMessage) throws IOException {

        ResultModel resultModel = new ResultModel(code, errorMessage);
        returnErrorMessage(response, resultModel);
    }

    public static void returnErrorMessage(HttpServletResponse response,
                                          ResultModel resultModel) throws IOException {
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        out.print(mapper.writeValueAsString(resultModel));
        out.flush();
    }
}
