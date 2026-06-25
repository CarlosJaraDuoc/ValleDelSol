package com.valledelsol.bff_service.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String REPORTES_URL = "http://localhost:8081/api/v1/reportes";
    private final String ALERTAS_URL  = "http://localhost:8082/api/v1/alertas";

    @GetMapping
    public Map<String, Object> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        try {
            Object reportes = restTemplate.getForObject(REPORTES_URL, Object.class);
            dashboard.put("reportes", reportes);
        } catch (Exception e) {
            dashboard.put("reportes", "Servicio no disponible");
        }

        try {
            Object alertas = restTemplate.getForObject(ALERTAS_URL, Object.class);
            dashboard.put("alertas", alertas);
        } catch (Exception e) {
            dashboard.put("alertas", "Servicio no disponible");
        }

        dashboard.put("timestamp", LocalDateTime.now().toString());
        return dashboard;
    }
}