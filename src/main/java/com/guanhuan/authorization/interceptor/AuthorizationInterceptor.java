package com.guanhuan.authorization.interceptor;

import com.guanhuan.authorization.entity.CheckResult;
import com.guanhuan.authorization.manager.TokenManager;
import com.guanhuan.common.utils.IpUtil;
import com.guanhuan.common.utils.JwtUtil;
import com.guanhuan.config.Constants;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * @see com.guanhuan.authorization.annotation.Authorization
 * @date 2017-11-9 17:11:19
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        //验证token
        CheckResult result = manager.checkToken(authorization);
        if(result.getCode() < 0){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String ip = null;
            try {
                ip = IpUtil.getIpAddr(request);
            } catch (Exception e) {
                logger.info("无法获取到IP地址",e);
            }
            logger.info("ip为：["+ip+"]的客户端未登陆");
            return false;
        }
        //成功解析
        request.setAttribute(Constants.CURRENT_USER_ID, result.getClaims().getSubject());
        return true;
//        TokenModel model = manager.getToken(authorization);
//        if (manager.checkToken(model)) {
//            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
//            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
//            return true;
//        }
//        //如果验证token失败，并且方法注明了Authorization，返回401错误
//        if (method.getAnnotation(Authorization.class) != null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
//        return true;
    }
}
