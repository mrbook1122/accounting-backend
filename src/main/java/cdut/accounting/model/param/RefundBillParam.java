package cdut.accounting.model.param;

import java.util.List;

/**
 * 报销账单参数
 */
public class RefundBillParam {
    private List<Integer> billIdList;
    private int accountId;

    public RefundBillParam() {
    }

    public RefundBillParam(List<Integer> billIdList, int accountId) {
        this.billIdList = billIdList;
        this.accountId = accountId;
    }

    public List<Integer> getBillIdList() {
        return billIdList;
    }

    public void setBillIdList(List<Integer> billIdList) {
        this.billIdList = billIdList;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
