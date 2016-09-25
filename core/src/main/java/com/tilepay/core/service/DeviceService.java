package com.tilepay.core.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.dto.DeviceRegistrationDto;
import com.tilepay.core.model.Device;
import com.tilepay.core.repository.DeviceRepository;

@Service
public class DeviceService {

    @Inject
    private DeviceRepository deviceRepository;
    
    public void save(DeviceRegistrationDto deviceDto){
    	Device device = new Device();
    	device.setAddress(deviceDto.getBTCaddress());
    	device.setRegistrationToken(deviceDto.getRegistrationToken());
    	device.setIPAddress(deviceDto.getIPAddress());
    	device.setName(deviceDto.getName());
    	device.setTxHash(deviceDto.getTxHash());
    	deviceRepository.save(device);
    }
    
    public Device getOne(Long id){
    	return deviceRepository.getOne(id);
    }
    
    public List<Device> getAll(){
    	return deviceRepository.findAll();
    }
}
