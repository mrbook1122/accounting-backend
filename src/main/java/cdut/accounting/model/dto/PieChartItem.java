package cdut.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PieChartItem {
    private String item;
    private double percent;
}
