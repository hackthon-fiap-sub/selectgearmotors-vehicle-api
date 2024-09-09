package br.com.selectgearmotors.vehicle.core.service;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.ports.in.vehiclecategory.*;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleCategoryRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleCategoryService implements CreateVehicleCategoryPort, UpdateVehicleCategoryPort, FindByIdVehicleCategoryPort, FindVehicleCategoriesPort, DeleteVehicleCategoryPort {

    private final VehicleCategoryRepositoryPort vehicleCategoryRepository;

    @Override
    public VehicleCategory save(VehicleCategory product) {
        return vehicleCategoryRepository.save(product);
    }

    @Override
    public VehicleCategory update(Long id, VehicleCategory product) {
        VehicleCategory resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, product);

            return vehicleCategoryRepository.save(resultById);
        }

        return null;
    }

    @Override
    public VehicleCategory findById(Long id) {
        return vehicleCategoryRepository.findById(id);
    }

    @Override
    public List<VehicleCategory> findAll() {
       return vehicleCategoryRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            VehicleCategory vehicleCategoryById = findById(id);
            if (vehicleCategoryById == null) {
                throw new ResourceFoundException("Product Category not found");
            }

            vehicleCategoryRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
