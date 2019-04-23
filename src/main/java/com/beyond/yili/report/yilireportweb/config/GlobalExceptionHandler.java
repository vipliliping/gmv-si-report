package com.beyond.yili.report.yilireportweb.config;

import com.beyond.yili.common.exception.ForbiddenException;
import com.beyond.yili.common.exception.NoAuthException;
import com.beyond.yili.common.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vipliliping
 * @create 2019/4/23 11:30
 * @desc
 **/
@Controller
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error(e.getMessage(), e);
        return Result.unknown(e.getMessage());
    }

    @ExceptionHandler({NoAuthException.class})
    public String noAuthHandler(HttpServletRequest req, Exception e) {
        log.warn("noAuth Handler,Host: {} invokes url {} ERROR: {}", new Object[]{req.getRemoteHost(), req.getRequestURL(), e.getMessage()});
        req.setAttribute("javax.servlet.error.status_code", Integer.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return "forward:/error";
    }

    @ExceptionHandler({ForbiddenException.class})
    public String forbiddenHandler(HttpServletRequest req, Exception e) {
        log.warn("forbidden Handler,Host: {} invokes url {} ERROR: {}", new Object[]{req.getRemoteHost(), req.getRequestURL(), e.getMessage()});
        req.setAttribute("javax.servlet.error.status_code", Integer.valueOf(HttpStatus.FORBIDDEN.value()));
        return "forward:/error";
    }
}
