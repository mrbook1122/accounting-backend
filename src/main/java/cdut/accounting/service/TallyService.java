package cdut.accounting.service;

import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.dto.UserBillListDTO;
import cdut.accounting.model.param.BillParam;

import java.util.List;

public interface TallyService {
    /**
     * 获取用户账单
     *
     * @param date '2020-07'表示月度账单，'2020-07-01'表示日度账单
     */
    UserBillAnalysisDTO getUserBill(String date);

    /**
     * 保存用户账单
     */
    void saveUserBill(BillParam billParam);

    /**
     * 获取用户账单列表
     * （获取当天的账单列表）
     */
    List<UserBillDTO> getUserBillList();
}
