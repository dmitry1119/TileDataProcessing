package com.tilepay.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.core.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

}
