package cdut.accounting.component;

import cdut.accounting.exception.*;
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

    @ExceptionHandler(FinanceAccountExistsException.class)
    public CommonResult financeAccountExists() {
        return new CommonResult(false, "账户已存在");
    }

    @ExceptionHandler(FinanceAccountNotExistsException.class)
    public CommonResult financeAccountNotExists() {
        return new CommonResult(false, "关联账户不存在");
    }

    @ExceptionHandler
    public CommonResult joinTeamException(JoinTeamException ex) {
        return new CommonResult(false, ex.getMessage());
    }
}
