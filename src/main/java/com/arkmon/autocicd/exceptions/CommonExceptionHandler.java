package com.arkmon.autocicd.exceptions;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author X.J
 * @date 2021/2/7
 */

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @Resource
    private NotificationService ns;

    /**
     * 拦截MethodArgumentNotValidException类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ReturnData methodDtoNotValidException(MethodArgumentNotValidException e) {

        log.error("{}", e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        return new ReturnData("-1", errorMsg.toString());
    }

    /**
     *  拦截MAException类的异常
     * @param e
     * @return
     */
    @ExceptionHandler(MAException.class)
    @ResponseBody
    public ReturnData maException(MAException e) {
        ns.notifyByDingTalk(null, "部署过程出现异常: " + e.toString());
        log.error("{}", e);
        String errCode = "";
        if (e.getParameters() !=null && e.getParameters().length != 0) {
            errCode = String.valueOf(e.getParameters()[0]);
        }
        String msgCode = StringUtils.isEmpty(errCode) ? "-1" : errCode;
        return new ReturnData(msgCode, e.getLocalizedMessage());
    }


//    /**
//     *  拦截MAException类的异常
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseBody
//    public ReturnData httpMessageNotReadableException(HttpMessageNotReadableException e) {
//        log.error("{}", e);
//        String errMsg;
//        Throwable cause = e.getCause();
//        errMsg = cause.getMessage().substring(cause.getMessage().indexOf(":") + 1, cause.getMessage().lastIndexOf("at")).replace("\n", "");
//        return new ReturnData("-1", errMsg);
//    }

    /**
     *  拦截其他Exception类的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnData exception(Exception e) {
        log.error("{}", e);
        return new ReturnData("-1", "未知错误 (Unknown Error)");
    }
}
