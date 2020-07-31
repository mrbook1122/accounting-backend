package cdut.accounting.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FinanceAccountDTO {
    private int id;
    private String type;
    private String name;
    private double balance;

    public FinanceAccountDTO(int id, String type, String name, double balance) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.balance = balance;
    }
}
