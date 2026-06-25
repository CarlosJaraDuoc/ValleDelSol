package com.valledelsol.ms_alertas;

import com.valledelsol.ms_alertas.controller.AlertaController;
import com.valledelsol.ms_alertas.model.Alerta;
import com.valledelsol.ms_alertas.service.AlertaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertaControllerTest {

    @Mock
    private AlertaService alertaService;

    @InjectMocks
    private AlertaController alertaController;

    private Alerta alerta;

    @BeforeEach
    void setUp() {
        alerta = new Alerta();
        alerta.setId(1L);
        alerta.setMensaje("Evacuación inmediata");
        alerta.setNivel("CRÍTICO");
        alerta.setComuna("ValleDelSol");
        alerta.setActiva(true);
    }

    @Test
    void testGetAll() {
        when(alertaService.findAll()).thenReturn(Arrays.asList(alerta));
        List<Alerta> result = alertaController.getAll();
        assertEquals(1, result.size());
        assertEquals("CRÍTICO", result.get(0).getNivel());
    }

    @Test
    void testGetByComuna() {
        when(alertaService.findByComuna("ValleDelSol")).thenReturn(Arrays.asList(alerta));
        List<Alerta> result = alertaController.getByComuna("ValleDelSol");
        assertEquals(1, result.size());
        assertEquals("ValleDelSol", result.get(0).getComuna());
    }

    @Test
    void testGetByIdFound() {
        when(alertaService.findById(1L)).thenReturn(Optional.of(alerta));
        ResponseEntity<Alerta> response = alertaController.getById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Evacuación inmediata", response.getBody().getMensaje());
    }

    @Test
    void testGetByIdNotFound() {
        when(alertaService.findById(99L)).thenReturn(Optional.empty());
        ResponseEntity<Alerta> response = alertaController.getById(99L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testCreate() {
        when(alertaService.save(alerta)).thenReturn(alerta);
        Alerta result = alertaController.create(alerta);
        assertNotNull(result);
        assertEquals("CRÍTICO", result.getNivel());
    }

    @Test
    void testDelete() {
        doNothing().when(alertaService).deleteById(1L);
        ResponseEntity<Void> response = alertaController.delete(1L);
        assertEquals(204, response.getStatusCode().value());
    }
}