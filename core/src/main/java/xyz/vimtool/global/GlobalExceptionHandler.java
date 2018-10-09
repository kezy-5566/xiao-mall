package xyz.vimtool.global;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.vimtool.response.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/10/9
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("Host {}, Url {}, ParameterMap {}, ERROR: {}",
                req.getRemoteAddr(), req.getRequestURL(), req.getParameterMap(), e.getMessage());
        if (log.isDebugEnabled()) {
            log.debug("Debug exception = {} ", ExceptionUtils.getTrace(e));
        }
        return HttpResponse.buildFailed();
    }

    @ExceptionHandler(value = ResponseException.class)
    @ResponseBody
    public Object responseExceptionHandler(HttpServletRequest req, ResponseException e) {
        log.error("Host {}, Url {}, ParameterMap {}, ERROR: {}",
                req.getRemoteAddr(), req.getRequestURL(), req.getParameterMap(), e.getMessage());
        if (log.isDebugEnabled()) {
            log.debug("Debug exception = {} ", ExceptionUtils.getTrace(e));
        }
        return HttpResponse.build(e.getErrorCode());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public Object unauthorizedExceptionHandler(HttpServletRequest req, UnauthorizedException e) {
        log.error("Host {}, Url {}, ParameterMap {}, ERROR: {}",
                req.getRemoteAddr(), req.getRequestURL(), req.getParameterMap(), e.getMessage());
        if (log.isDebugEnabled()) {
            log.debug("Debug exception = {} ", ExceptionUtils.getTrace(e));
        }
        return HttpResponse.build(HttpCode.NO_AUTH);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object responseExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        StringBuffer message = new StringBuffer();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            message.append(violation.getMessage()).append(",");
        }
        message.deleteCharAt(message.length() - 1);
        log.error("Host {}, Url {}, ParameterMap {}, ERROR: {}",
                req.getRemoteAddr(), req.getRequestURL(), req.getParameterMap(), message);
        if (log.isDebugEnabled()) {
            log.debug("Debug exception = {} ", ExceptionUtils.getTrace(e));
        }
        return HttpResponse.build(MarkCode.build(message.toString()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        if(e.getBindingResult().hasErrors()) {
            StringBuffer message = new StringBuffer();
            for(ObjectError error : e.getBindingResult().getAllErrors()) {
                message.append(error.getDefaultMessage()).append(",");
            }
            message.deleteCharAt(message.length() - 1);
            log.error("Host {}, Url {}, ParameterMap {}, ERROR: {}",
                    req.getRemoteAddr(), req.getRequestURL(), req.getParameterMap(), message);
            if (log.isDebugEnabled()) {
                log.debug("Debug exception = {} ", ExceptionUtils.getTrace(e));
            }
            return HttpResponse.build(MarkCode.build(message.toString()));
        }
        return HttpResponse.build(MarkCode.build("参数错误"));
    }
}
