package com.letseat.global.config.interceptor;

import com.letseat.global.config.interceptor.LoginUserId;
import com.letseat.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static com.letseat.global.constant.ProMoConstant.BEARER;
import static com.letseat.global.constant.ProMoConstant.SPACE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class LoginUserIdArgumentResolver implements HandlerMethodArgumentResolver {
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUserId.class) && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String[] authorizations = Objects.requireNonNull(webRequest.getHeader(AUTHORIZATION)).split(SPACE);
        String type = authorizations[HEADER_KEY_INDEX];
        String accessToken = authorizations[HEADER_VALUE_INDEX];

        if (!type.equalsIgnoreCase(BEARER)) {
            throw new IllegalArgumentException();
        }

        return jwtProvider.getPayloadByToken(accessToken);
    }
}
