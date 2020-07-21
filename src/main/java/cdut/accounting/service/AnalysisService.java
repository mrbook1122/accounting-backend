package cdut.accounting.service;

import cdut.accounting.model.dto.HistogramDTO;
import cdut.accounting.model.dto.PieChartDTO;

import java.util.Date;

public interface AnalysisService {
    /**
     * 获取用户月收支柱状图统计数据
     */
    HistogramDTO getUserHistogram(Date date, String username);

    /**
     * 获取用户月收支饼状图统计数据
     */
    PieChartDTO getUserPieChart(Date date, String username);
}
