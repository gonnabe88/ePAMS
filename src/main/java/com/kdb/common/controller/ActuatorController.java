package com.kdb.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote Ac
 * @since 2024-06-09
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ActuatorController {

    @Autowired
    private HealthEndpoint healthEndpoint;    
    
    @Autowired
    private InfoEndpoint infoEndpoint;

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @Autowired
    private ObjectMapper objectMapper;

    private final LoggersEndpoint loggersEndpoint;
    
    @GetMapping("/actuator-data")
    public String getActuatorData(Model model) throws JsonProcessingException {
        // Health data
        model.addAttribute("health", healthEndpoint.health());
        
        // Metrics data
        model.addAttribute("metrics", metricsEndpoint.listNames());
        
        // Loggers data
        LoggersEndpoint.LoggersDescriptor loggersDescriptor = loggersEndpoint.loggers();
        model.addAttribute("loggers", loggersDescriptor.getLoggers());
        
        return "/common/actuator";
    }
}
