package com.valledelsol.ms_alertas.controller;

import com.valledelsol.ms_alertas.model.Alerta;
import com.valledelsol.ms_alertas.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
@CrossOrigin(origins = "*")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    public List<Alerta> getAll() {
        return alertaService.findAll();
    }

    @GetMapping("/comuna/{comuna}")
    public List<Alerta> getByComuna(@PathVariable String comuna) {
        return alertaService.findByComuna(comuna);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> getById(@PathVariable Long id) {
        return alertaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alerta create(@RequestBody Alerta alerta) {
        return alertaService.save(alerta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}