package com.valledelsol.ms_alertas.service;

import com.valledelsol.ms_alertas.model.Alerta;
import com.valledelsol.ms_alertas.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    public List<Alerta> findAll() {
        return alertaRepository.findAll();
    }

    public List<Alerta> findByComuna(String comuna) {
        return alertaRepository.findByComuna(comuna);
    }

    public Optional<Alerta> findById(Long id) {
        return alertaRepository.findById(id);
    }

    public Alerta save(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public void deleteById(Long id) {
        alertaRepository.deleteById(id);
    }
}