package com.guanhuan.authorization.resolvers;

import com.google.common.base.Strings;
import com.guanhuan.authorization.annotation.CurrentUser;
import com.guanhuan.config.Constants;
import com.guanhuan.entity.User;
import com.guanhuan.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * @see com.guanhuan.authorization.annotation.CurrentUser
 * @date 2017-11-8 11:19:07
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Autowired
    public CurrentUserMethodArgumentResolver(UserService userService) {
        this.userService = userService;
    }

    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        return (parameter.getParameterType().isAssignableFrom(User.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class));
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        String strId = (String)webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);

        if (!Strings.isNullOrEmpty(strId)) {
            Long currentUserId = new Long(strId);
            //从数据库中查询并返回
            return userService.findById(currentUserId);
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
