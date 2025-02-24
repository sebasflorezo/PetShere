package com.PetShere.service.implementation.service;

import com.PetShere.persistence.model.service.Service;
import com.PetShere.persistence.model.user.Role;
import com.PetShere.persistence.model.user.User;
import com.PetShere.persistence.repository.service.IServiceRepository;
import com.PetShere.persistence.repository.user.IUserRepository;
import com.PetShere.presentation.dto.service.ServiceDto;
import com.PetShere.service.interfaces.IServiceService;
import com.PetShere.util.AppUtil;
import com.PetShere.util.Constants;
import com.PetShere.util.mapper.service.ServiceMapper;
import lombok.RequiredArgsConstructor;

import javax.net.ssl.SSLSession;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements IServiceService {

    private final IUserRepository userRepository;
    private final IServiceRepository serviceRepository;

    public List<ServiceDto> getServices() {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND));

        if (user.getRole().equals(Role.ADMIN)) {
            return serviceRepository.findAll()
                    .stream()
                    .map(ServiceMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return serviceRepository.findAll()
                    .stream()
                    .filter(com.PetShere.persistence.model.service.Service::getState)
                    .map(ServiceMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public ServiceDto getService(Long id) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND));

        ServiceDto service = serviceRepository.findById(id)
                .map(ServiceMapper::toDto)
                .orElseThrow(() -> new RuntimeException(Constants.SERVICE_NOT_FOUND));

        if (user.getRole().equals(Role.ADMIN) || service.getState())
            return service;
        else
            throw new RuntimeException(Constants.SERVICE_NOT_FOUND);
    }

    public void changeServiceState(Long id) {
        User user = AppUtil.getCurrentUser(userRepository)
                .orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND));

        serviceRepository.findById(id).ifPresent(s -> {
            s.setState(!s.getState());
            serviceRepository.save(s);
        });
    }

    public Service createService(ServiceDto serviceDto) {
        // TODO: Agregar validaciones
        Service service = ServiceMapper.toEntity(serviceDto);
        if (serviceDto.getState() == null)
            service.setState(true);

        serviceRepository.save(service);
        return service;
    }

    public Service updateService(Long id, ServiceDto serviceDto) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constants.SERVICE_NOT_FOUND));

        service.setName(serviceDto.getName());
        service.setPrice(serviceDto.getPrice());
        service.setState(serviceDto.getState());

        serviceRepository.save(service);
        return service;
    }
}
