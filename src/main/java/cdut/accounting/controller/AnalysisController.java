package cdut.accounting.controller;

import cdut.accounting.model.dto.HistogramDTO;
import cdut.accounting.model.dto.PieChartDTO;
import cdut.accounting.service.AnalysisService;
import cdut.accounting.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RestController
public class AnalysisController {
    @Autowired
    private AnalysisService analysisService;

    /**
     * 用户月收柱状图数据
     */
    @GetMapping("/user/analysis/chart")
    public HistogramDTO getUserHistogram(Date date) {
        String username = JwtUtils.getUsername();
        return analysisService.getUserHistogram(date, username);
    }

    @GetMapping("/user/analysis/chart/category")
    public PieChartDTO getUserPieChart(Date date) {
        String username = JwtUtils.getUsername();
        return analysisService.getUserPieChart(date, username);
    }
}
