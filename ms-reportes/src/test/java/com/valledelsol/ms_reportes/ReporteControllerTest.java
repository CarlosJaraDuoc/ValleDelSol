
package com.valledelsol.ms_reportes;

import com.valledelsol.ms_reportes.controller.ReporteController;
import com.valledelsol.ms_reportes.model.Reporte;
import com.valledelsol.ms_reportes.service.ReporteService;
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
public class ReporteControllerTest {

    @Mock
    private ReporteService reporteService;

    @InjectMocks
    private ReporteController reporteController;

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        reporte = new Reporte();
        reporte.setId(1L);
        reporte.setTipoIncendio("Forestal");
        reporte.setCoordenadas("-33.4,-70.6");
        reporte.setDescripcion("Incendio sector norte");
        reporte.setEstado("ACTIVO");
    }

    @Test
    void testGetAll() {
        when(reporteService.findAll()).thenReturn(Arrays.asList(reporte));
        List<Reporte> result = reporteController.getAll();
        assertEquals(1, result.size());
        assertEquals("Forestal", result.get(0).getTipoIncendio());
    }

    @Test
    void testGetByIdFound() {
        when(reporteService.findById(1L)).thenReturn(Optional.of(reporte));
        ResponseEntity<Reporte> response = reporteController.getById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Forestal", response.getBody().getTipoIncendio());
    }

    @Test
    void testGetByIdNotFound() {
        when(reporteService.findById(99L)).thenReturn(Optional.empty());
        ResponseEntity<Reporte> response = reporteController.getById(99L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testCreate() {
        when(reporteService.save(reporte)).thenReturn(reporte);
        Reporte result = reporteController.create(reporte);
        assertNotNull(result);
        assertEquals("ACTIVO", result.getEstado());
    }

    @Test
    void testDelete() {
        doNothing().when(reporteService).deleteById(1L);
        ResponseEntity<Void> response = reporteController.delete(1L);
        assertEquals(204, response.getStatusCode().value());
    }
}