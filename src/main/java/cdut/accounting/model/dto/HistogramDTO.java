package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 柱状图dto
 */
@Data
public class HistogramDTO {
    private int[] income;
    private int[] expenses;
}
