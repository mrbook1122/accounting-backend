package cdut.accounting.model.document;

import lombok.Data;

/**
 * MongoDB返回的数据类型
 */
@Data
public class BillDocument {
    private String id;
    private double money;
}
