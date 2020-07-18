package cdut.accounting.controller;

import cdut.accounting.model.dto.UserBill;
import cdut.accounting.service.TallyService;
import cdut.accounting.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账单控制器
 */
@RestController
@RequestMapping("/user/bill")
public class TallyController {
    @Autowired
    private TallyService tallyService;

    /**
     * 获取用户账单
     */
    @GetMapping("")
    public UserBill getUserBill(String date) {
        DateUtils.validateDate(date);
        return tallyService.getUserBill(date);
    }
}
