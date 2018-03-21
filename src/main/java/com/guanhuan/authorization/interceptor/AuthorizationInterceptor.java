package com.guanhuan.authorization.interceptor;

import com.guanhuan.authorization.entity.CheckResult;
import com.guanhuan.authorization.manager.TokenManager;
import com.guanhuan.common.utils.CookieUtil;
import com.guanhuan.common.utils.HttpUtil;
import com.guanhuan.common.utils.IpUtil;
import com.guanhuan.config.CheckStatus;
import com.guanhuan.config.Constants;
import com.guanhuan.config.ResultStatus;
import com.guanhuan.entity.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * @see com.guanhuan.authorization.annotation.Authorization
 * @date 2017-11-9 17:11:19
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private final TokenManager manager;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    public AuthorizationInterceptor(TokenManager manager) {
        this.manager = manager;
    }

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //注册用户放行
        String uri = request.getRequestURI();
        if(uri.equals("/User/user") && request.getMethod().compareToIgnoreCase("post") == 0){
            return true;
        }
        logger.info("拦截:"+uri);
        //从header或者cookie中得到token
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        if(authorization == null || "".equals(authorization)){
            Cookie auth = CookieUtil.getCookieByName(request, Constants.AUTHORIZATION);
            if(request.getCookies() == null || request.getCookies().length == 0 || auth == null){
                HttpUtil.returnErrorMessage(response,
                        ResultModel.error(ResultStatus.USER_NOT_AUTH));
                return false;
            }
            authorization = auth.getValue();
        }

        String ip;
        CheckResult result;
        result = manager.checkToken(authorization);

        try {
            ip = IpUtil.getIpAddr(request);
        } catch (Exception e) {
            logger.info("无法获取到IP地址",e);
            return false;
        }

        if(result.getCode() < 0){
            //设置状态码
            ResultModel resultModel;
            switch (CheckStatus.getByValue(result.getCode())){
                case FAIL_EXPIRED :
                    resultModel = new ResultModel(ResultStatus.USER_EXPIRES_AUTH);
                    break;
                case FAIL_SIGNATURE:
                case FAIL_MALFORMED:
                case FAIL_CLAIMS:
                case FAIL_ISEMPTY:
                    resultModel = new ResultModel(ResultStatus.USER_TOKEN_ERROR);
                    break;
                default:
                    resultModel = new ResultModel(ResultStatus.USER_OTHER_ERROR);
                    break;
            }
            HttpUtil.returnErrorMessage(response, resultModel);
            logger.info("ip为：["+ip+"]的客户端未登陆");
            return false;
        }

        //成功解析
        logger.info("用户"+ip+"的id:"+result.getClaims().getSubject());
        request.setAttribute(Constants.CURRENT_USER_ID, result.getClaims().getSubject());
        return true;
    }
}
