package com.guanhuan.authorization.resolvers;

import com.guanhuan.authorization.interceptor.AuthorizationInterceptor;
import com.guanhuan.common.utils.HttpUtil;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对系统错误进行包装，转成json格式返回。
 * @author liguanhuan_a@163.com
 * @create 2017-11-28 17:14
 **/
public class DefaultExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Nullable
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

        //将返回码转为负数
        int code = response.getStatus();
        code = code < 0 ? code : -code;

        ResultModel.error(ResultStatus.SYS_ERROR);
        try {
            HttpUtil.returnErrorMessage(response, code, ex.getMessage());
            logger.error("出现异常:"+ex.getMessage());
            ex.printStackTrace();
        } catch (IOException e) {
            logger.error("resolveException异常:"+ e.getMessage(), e);
        }

        return new ModelAndView();
    }
}
