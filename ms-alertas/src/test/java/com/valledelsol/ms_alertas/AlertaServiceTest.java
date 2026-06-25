package com.valledelsol.ms_alertas;

import com.valledelsol.ms_alertas.model.Alerta;
import com.valledelsol.ms_alertas.repository.AlertaRepository;
import com.valledelsol.ms_alertas.service.AlertaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private AlertaService alertaService;

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
    void testFindAll() {
        when(alertaRepository.findAll()).thenReturn(Arrays.asList(alerta));
        List<Alerta> result = alertaService.findAll();
        assertEquals(1, result.size());
        assertEquals("CRÍTICO", result.get(0).getNivel());
        verify(alertaRepository, times(1)).findAll();
    }

    @Test
    void testFindByComuna() {
        when(alertaRepository.findByComuna("ValleDelSol")).thenReturn(Arrays.asList(alerta));
        List<Alerta> result = alertaService.findByComuna("ValleDelSol");
        assertEquals(1, result.size());
        assertEquals("ValleDelSol", result.get(0).getComuna());
    }

    @Test
    void testFindById() {
        when(alertaRepository.findById(1L)).thenReturn(Optional.of(alerta));
        Optional<Alerta> result = alertaService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Evacuación inmediata", result.get().getMensaje());
    }

    @Test
    void testFindByIdNotFound() {
        when(alertaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Alerta> result = alertaService.findById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        when(alertaRepository.save(alerta)).thenReturn(alerta);
        Alerta result = alertaService.save(alerta);
        assertNotNull(result);
        assertTrue(result.getActiva());
        verify(alertaRepository, times(1)).save(alerta);
    }

    @Test
    void testDeleteById() {
        doNothing().when(alertaRepository).deleteById(1L);
        alertaService.deleteById(1L);
        verify(alertaRepository, times(1)).deleteById(1L);
    }
}