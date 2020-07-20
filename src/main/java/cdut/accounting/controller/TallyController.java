package cdut.accounting.controller;

import cdut.accounting.model.dto.CommonResult;
import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillListDTO;
import cdut.accounting.model.param.BillParam;
import cdut.accounting.service.TallyService;
import cdut.accounting.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 账单控制器
 */
@RestController
@RequestMapping("/user/bill")
public class TallyController {
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
    public UserBillListDTO getUserBillList(@RequestParam(required = false, defaultValue = "1") int page) {
        return tallyService.getUserBillList(page - 1);
    }
}
