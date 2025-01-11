package br.com.selectgearmotors.vehicle.core.service;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.core.ports.in.vehicle.*;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleRepositoryPort;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleService implements CreateVehiclePort, UpdateVehiclePort, FindByIdVehiclePort, FindVehiclesPort, DeleteVehiclePort {

    private final VehicleRepositoryPort vehicleRepository;

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Long id, Vehicle vehicle) {
        Vehicle resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, vehicle);

            return vehicleRepository.save(resultById);
        }

        return null;
    }

    @Override
    public void updateReserved(String code) {
        vehicleRepository.updateReserved(code);
    }

    @Override
    public void updateSold(String code) {
        vehicleRepository.updateSold(code);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle findByCode(String code) {
        return vehicleRepository.findByCode(code);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Vehicle vehicleById = findById(id);
            if (vehicleById == null) {
                throw new ResourceFoundException("Vehicle not found");
            }

            vehicleRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
