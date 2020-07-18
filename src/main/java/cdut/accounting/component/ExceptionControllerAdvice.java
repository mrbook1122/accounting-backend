package cdut.accounting.component;

import cdut.accounting.exception.DateFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理
 */
public class ExceptionControllerAdvice {
    /**
     * 日期格式错误
     */
    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<String> dateFormatError() {
        return ResponseEntity.badRequest().body("日期格式不正确");
    }
}
