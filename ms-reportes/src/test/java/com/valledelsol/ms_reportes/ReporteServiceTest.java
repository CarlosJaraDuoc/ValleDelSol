package com.valledelsol.ms_reportes;

import com.valledelsol.ms_reportes.model.Reporte;
import com.valledelsol.ms_reportes.repository.ReporteRepository;
import com.valledelsol.ms_reportes.service.ReporteService;
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
public class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

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
    void testFindAll() {
        when(reporteRepository.findAll()).thenReturn(Arrays.asList(reporte));
        List<Reporte> result = reporteService.findAll();
        assertEquals(1, result.size());
        assertEquals("Forestal", result.get(0).getTipoIncendio());
        verify(reporteRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporte));
        Optional<Reporte> result = reporteService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Forestal", result.get().getTipoIncendio());
    }

    @Test
    void testFindByIdNotFound() {
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Reporte> result = reporteService.findById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        when(reporteRepository.save(reporte)).thenReturn(reporte);
        Reporte result = reporteService.save(reporte);
        assertNotNull(result);
        assertEquals("ACTIVO", result.getEstado());
        verify(reporteRepository, times(1)).save(reporte);
    }

    @Test
    void testDeleteById() {
        doNothing().when(reporteRepository).deleteById(1L);
        reporteService.deleteById(1L);
        verify(reporteRepository, times(1)).deleteById(1L);
    }
}