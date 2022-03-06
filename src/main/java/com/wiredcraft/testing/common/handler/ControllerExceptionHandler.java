package com.wiredcraft.testing.common.handler;

import com.wiredcraft.testing.common.enums.ResultCode;
import com.wiredcraft.testing.common.exception.ServiceException;
import com.wiredcraft.testing.common.vo.FieldValidatorError;
import com.wiredcraft.testing.common.vo.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LogManager.getLogger(getClass());

    private static final String logExceptionFormat = "[EXIGENCE] Some thing wrong with the system: %s";

    /**
     * 自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public R handleServiceException(HttpServletRequest request, ServiceException ex) {
        logger.error("biz error", ex);
        return R.error(ex.getResultCode());
    }

    /**
     * MethodArgumentNotValidException: 实体类属性校验不通过
     * 如: listUsersValid(@RequestBody @Valid UserFilterOption option)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleMethodArgumentNotValid(HttpServletRequest request, MethodArgumentNotValidException ex) {
//        logger.error(ex);
        return validatorErrors(ex.getBindingResult());
    }

    private R validatorErrors(BindingResult result) {
        List<FieldValidatorError> errors = new ArrayList<FieldValidatorError>();
        for (FieldError error : result.getFieldErrors()) {
            errors.add(toFieldValidatorError(error));
        }
        return R.error(ResultCode.PARAM_ERROR, errors);
    }

    /**
     * ConstraintViolationException: 直接对方法参数进行校验，校验不通过。
     * 如: pageUsers(@RequestParam @Min(1)int pageIndex, @RequestParam @Max(100)int pageSize)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R handleConstraintViolationException(HttpServletRequest request,
                                                ConstraintViolationException ex) {
        logger.error(ex);
        List<FieldValidatorError> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(toFieldValidatorError(violation));
        }
        return R.error(ResultCode.PARAM_ERROR, errors);
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public R handleConstraintViolationException(HttpServletRequest request,
                                                MissingServletRequestParameterException ex) {
        return R.error(ResultCode.PARAM_ERROR, ex.getMessage());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R handleIllegalArgumentException(HttpServletRequest request,
                                            IllegalArgumentException ex) {
        return R.error(ResultCode.PARAM_ERROR, ex.getMessage());
    }

    private FieldValidatorError toFieldValidatorError(ConstraintViolation<?> violation) {
        Path.Node lastNode = null;
        for (Path.Node node : violation.getPropertyPath()) {
            lastNode = node;
        }
        FieldValidatorError fieldNotValidError = new FieldValidatorError();
        fieldNotValidError.setField(lastNode.getName());
        fieldNotValidError.setMessage(violation.getMessage());
        return fieldNotValidError;
    }

    private FieldValidatorError toFieldValidatorError(FieldError error) {
        FieldValidatorError fieldNotValidError = new FieldValidatorError();
        fieldNotValidError.setField(error.getField());
        fieldNotValidError.setMessage(error.getDefaultMessage());
        return fieldNotValidError;
    }


    /**
     * others exceptions
     */
    @ExceptionHandler()
    @ResponseBody
    public R handleAll(HttpServletRequest request, Exception ex) {
        return internalServiceError(ex);
    }


    private R internalServiceError(Exception ex) {
        logException(ex);
        // do something else
        return R.error(ResultCode.ERROR);
    }

    private <T extends Throwable> void logException(T e) {
        logger.error(String.format(logExceptionFormat, e.getMessage()), e);
    }
}