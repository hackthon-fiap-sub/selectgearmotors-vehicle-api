package br.com.selectgearmotors.vehicle.core.service;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.ports.in.vehicletype.*;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleTypeRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleTypeService implements CreateVehicleTypePort, UpdateVehicleTypePort, FindByIdVehicleTypePort, FindVehicleTypesPort, DeleteVehicleTypePort {

    private final VehicleTypeRepositoryPort vehicleTypeRepository;

    @Override
    public VehicleType save(VehicleType product) {
        return vehicleTypeRepository.save(product);
    }

    @Override
    public VehicleType update(Long id, VehicleType product) {
        VehicleType resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return vehicleTypeRepository.save(resultById);
        }

        return null;
    }

    @Override
    public VehicleType findById(Long id) {
        return vehicleTypeRepository.findById(id);
    }

    @Override
    public List<VehicleType> findAll() {
       return vehicleTypeRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            VehicleType vehicleTypeById = findById(id);
            if (vehicleTypeById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            vehicleTypeRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
