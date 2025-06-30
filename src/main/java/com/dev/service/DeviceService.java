package com.dev.service;

import com.dev.model.Device;
import com.dev.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public List<Device> findAll() {
        return repository.findAll();
    }

    public Optional<Device> findById(Long id) {
        return repository.findById(id);
    }

    public Device save(Device device) {
        return repository.save(device);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Device> findByStatus(String status) {
        return repository.findByStatus(status);
    }
}
