package cdut.accounting.service;

import cdut.accounting.model.dto.RefundDTO;
import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.param.BillParam;
import cdut.accounting.model.param.RefundBillParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
     * <p>用户账单中冗余存储账户信息, 当财产账户删除之后，账单中仍然会保留账户信息</p>
     */
    @Transactional
    void saveUserBill(BillParam billParam);

    /**
     * 获取用户账单列表
     */
    String getUserBillList(int userId, Date date);

    List<RefundDTO> getNonRefundList(int userId, Date date);

    List<RefundDTO> getRefundList(int userId, Date date);

    @Transactional
    void refundBill(RefundBillParam param);
}
