package cdut.accounting.controller;

import cdut.accounting.model.dto.CommonResult;
import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.entity.User;
import cdut.accounting.model.param.BillParam;
import cdut.accounting.service.TallyService;
import cdut.accounting.utils.DateUtils;
import cdut.accounting.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 账单控制器
 */
@RestController
@RequestMapping("/user/bill")
public class TallyController {
    public static final Logger logger = LoggerFactory.getLogger(TallyController.class);

    @Autowired
    private TallyService tallyService;

    /**
     * 获取用户账单概览
     */
    @GetMapping("")
    public UserBillAnalysisDTO getUserBill(String date) {
        DateUtils.validateDate(date);
        return tallyService.getUserBill(date);
    }

    /**
     * 提交账单
     */
    @PostMapping("")
    public CommonResult postUserBill(@RequestBody BillParam billParam) {
        tallyService.saveUserBill(billParam);
        return new CommonResult(true, "操作成功");
    }

    /**
     * 获取用户账单列表
     */
    @GetMapping("/list")
    public void getUserBillList(@RequestParam String date, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Calendar time = Calendar.getInstance();
        if (date.length() == 10) {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));
            time.set(year, month - 1, day, 0, 0, 0);
        }
        String email = JwtUtils.getUserEmail();
        String result =  tallyService.getUserBillList(email, time.getTime());
        response.getWriter().write(result);
    }
}
