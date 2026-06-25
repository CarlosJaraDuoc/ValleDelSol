package com.valledelsol.ms_alertas.repository;

import com.valledelsol.ms_alertas.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByComuna(String comuna);
}