package com.oficina.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oficina.api.dto.DashboardResumoDTO;
import com.oficina.api.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    public DashboardResumoDTO resumo() {
        return dashboardService.resumo();
    }
}
