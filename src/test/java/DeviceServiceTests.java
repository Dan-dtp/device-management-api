package com.dev;

import com.dev.model.Device;
import com.dev.repository.DeviceRepository;
import com.dev.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTests {

    @Mock
    private DeviceRepository repository;

    @InjectMocks
    private DeviceService service;

    private Device device;

    @BeforeEach
    public void setup() {
        device = new Device();
        device.setId(1L);
        device.setName("Test Device");
        device.setType("Sensor");
        device.setStatus("active");
        device.setCreatedAt(LocalDate.now());
    }

    @Test
    public void testFindAll() {
        List<Device> devices = List.of(device);
        when(repository.findAll()).thenReturn(devices);

        List<Device> result = service.findAll();
        assertThat(result).hasSize(1);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        Optional<Device> result = service.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Device");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Device> result = service.findById(2L);
        assertThat(result).isEmpty();
        verify(repository, times(1)).findById(2L);
    }

    @Test
    public void testSave() {
        when(repository.save(device)).thenReturn(device);

        Device saved = service.save(device);
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Device");
        verify(repository, times(1)).save(device);
    }

    @Test
    public void testDelete() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByStatus() {
        List<Device> activeDevices = List.of(device);
        when(repository.findByStatus("active")).thenReturn(activeDevices);

        List<Device> result = service.findByStatus("active");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("active");
        verify(repository, times(1)).findByStatus("active");
    }
}