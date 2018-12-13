package com.itis.service.repository;

import com.itis.service.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findByToken(String token);

}
