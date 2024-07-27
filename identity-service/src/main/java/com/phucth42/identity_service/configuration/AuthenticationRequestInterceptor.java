package com.phucth42.identity_service.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var authHeader = attributes.getRequest().getHeader("Authorization");
        log.info("Authorization header: {}", authHeader);
        if (StringUtils.hasText(authHeader)) {
            requestTemplate.header("Authorization", authHeader);
        }
    }
}
