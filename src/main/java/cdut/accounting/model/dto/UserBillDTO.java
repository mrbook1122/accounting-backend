package cdut.accounting.model.dto;

import cdut.accounting.model.dto.base.OutputConverter;
import cdut.accounting.model.entity.Tally;
import cdut.accounting.model.param.BillParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserBillDTO extends BillParam implements OutputConverter<UserBillDTO, Tally> {
    private String id;

    @Override
    public UserBillDTO convertFrom(Tally tally) {
        BeanUtils.copyProperties(tally, this);
        return this;
    }
}
