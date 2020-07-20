package cdut.accounting.service;

import cdut.accounting.model.dto.UserBill;
import cdut.accounting.model.param.BillParam;

public interface TallyService {
    /**
     * 获取用户账单
     *
     * @param date '2020-07'表示月度账单，'2020-07-01'表示日度账单
     */
    UserBill getUserBill(String date);

    /**
     * 保存用户账单
     */
    void saveUserBill(BillParam billParam);
}
