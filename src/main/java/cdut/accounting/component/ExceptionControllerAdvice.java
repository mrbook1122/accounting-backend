package cdut.accounting.component;

import cdut.accounting.exception.DateFormatException;
import cdut.accounting.exception.UserExistsException;
import cdut.accounting.model.dto.CommonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    /**
     * 日期格式错误
     */
    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<String> dateFormatError() {
        return ResponseEntity.badRequest().body("日期格式不正确");
    }

    @ExceptionHandler(UserExistsException.class)
    public CommonResult userExists() {
        return new CommonResult(false, "邮箱已注册");
    }
}
