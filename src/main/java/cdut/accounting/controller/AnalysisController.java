package cdut.accounting.controller;

import cdut.accounting.model.dto.HistogramDTO;
import cdut.accounting.model.dto.PieChartDTO;
import cdut.accounting.service.AnalysisService;
import cdut.accounting.utils.DateUtils;
import cdut.accounting.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public HistogramDTO getUserHistogram(String date) {
        String username = JwtUtils.getUsername();
        Date time = DateUtils.convertByMonth(date);
        return analysisService.getUserHistogram(time, username);
    }

    @GetMapping("/user/analysis/chart/category")
    public PieChartDTO getUserPieChart(String date) {
        Date time = DateUtils.convertByMonth(date);
        String username = JwtUtils.getUsername();
        return analysisService.getUserPieChart(time, username);
    }

    /**
     * 团队月收柱状图数据
     */
    @GetMapping("/team/{id}/analysis/chart")
    public HistogramDTO getTeamHistogram(String date, @PathVariable int id) {
        Date time = DateUtils.convertByMonth(date);
        return analysisService.getTeamHistogram(time, id);
    }

    @GetMapping("/team/{id}/analysis/chart/category")
    public PieChartDTO getTeamPieChart(String date, @PathVariable int id) {
        Date time = DateUtils.convertByMonth(date);
        return analysisService.getTeamPieChart(time, id);
    }
}
