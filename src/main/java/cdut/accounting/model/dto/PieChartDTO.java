package cdut.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 饼状图dto
 */
@Data
public class PieChartDTO {
    private List<PieChartItem> expenses;
    private List<PieChartItem> income;
}
