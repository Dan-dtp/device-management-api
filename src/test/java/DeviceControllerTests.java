package com.dev;

import com.dev.model.Device;
import com.dev.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void testCreateAndGetDevice() throws Exception {
        Device device = new Device();
        device.setName("Test Device");
        device.setType("Sensor");
        device.setStatus("active");
        device.setCreatedAt(LocalDate.now());

        // Create device
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Device"))
                .andExpect(jsonPath("$.type").value("Sensor"))
                .andExpect(jsonPath("$.status").value("active"));

        // Get all devices, should contain the created device
        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Test Device"));
    }

    @Test
    public void testUpdateDevice() throws Exception {
        Device device = new Device();
        device.setName("Old Name");
        device.setType("Sensor");
        device.setStatus("inactive");
        device.setCreatedAt(LocalDate.now());

        device = repository.save(device);

        Device updatedDevice = new Device();
        updatedDevice.setName("New Name");
        updatedDevice.setType("Actuator");
        updatedDevice.setStatus("active");
        updatedDevice.setCreatedAt(device.getCreatedAt());

        mockMvc.perform(put("/devices/" + device.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDevice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.type").value("Actuator"))
                .andExpect(jsonPath("$.status").value("active"));
    }

    @Test
    public void testDeleteDevice() throws Exception {
        Device device = new Device();
        device.setName("ToDelete");
        device.setType("Sensor");
        device.setStatus("inactive");
        device.setCreatedAt(LocalDate.now());

        device = repository.save(device);

        mockMvc.perform(delete("/devices/" + device.getId()))
                .andExpect(status().isNoContent());

        // Confirm deletion
        mockMvc.perform(get("/devices/" + device.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchByStatus() throws Exception {
        Device activeDevice = new Device();
        activeDevice.setName("ActiveDevice");
        activeDevice.setType("Sensor");
        activeDevice.setStatus("active");
        activeDevice.setCreatedAt(LocalDate.now());

        Device inactiveDevice = new Device();
        inactiveDevice.setName("InactiveDevice");
        inactiveDevice.setType("Sensor");
        inactiveDevice.setStatus("inactive");
        inactiveDevice.setCreatedAt(LocalDate.now());

        repository.save(activeDevice);
        repository.save(inactiveDevice);

        mockMvc.perform(get("/devices/search")
                        .param("status", "active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("ActiveDevice"));
    }
}
