package cdut.accounting.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户账单列表dto
 */
@Data
public class UserBillListDTO {
    private int pageLength;
    private List<UserBillDTO> items;
}
