package com.itis.service.service.impl;

import com.itis.service.dto.RegisterDeviceDto;
import com.itis.service.dto.UnregisterDeviceDto;
import com.itis.service.entity.Device;
import com.itis.service.entity.Student;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.mapper.DeviceMapper;
import com.itis.service.repository.DeviceRepository;
import com.itis.service.repository.StudentRepository;
import com.itis.service.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final StudentRepository studentRepository;
    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(
            StudentRepository studentRepository,
            DeviceRepository deviceRepository,
            DeviceMapper deviceMapper) {
        this.studentRepository = studentRepository;
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Transactional
    public void registerDevice(RegisterDeviceDto registerDeviceDto, String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException("Пользователь с e-mail" + email + " не найден");
        }

        Device device = deviceMapper.device(registerDeviceDto);

        student.getDevices().add(device);
        device.setStudent(student);

        deviceRepository.saveAndFlush(device);
        studentRepository.saveAndFlush(student);
    }

    public void unregisterDevice(UnregisterDeviceDto unregisterDeviceDto) {
        Device device = deviceRepository.findByToken(unregisterDeviceDto.getToken());
        if (device == null) {
            throw new ResourceNotFoundException("Устройство не найдено");
        }

        deviceRepository.delete(device);
    }

}
