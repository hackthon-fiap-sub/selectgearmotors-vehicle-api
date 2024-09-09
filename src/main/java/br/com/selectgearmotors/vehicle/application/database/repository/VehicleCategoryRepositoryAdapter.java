package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleCategoryMapper;
import br.com.selectgearmotors.vehicle.commons.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleCategoryRepositoryPort;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleCategoryRepositoryAdapter implements VehicleCategoryRepositoryPort {

    private final VehicleCategoryRepository vehicleCategoryRepository;
    private final VehicleCategoryMapper vehicleCategoryMapper;

    @Override
    public VehicleCategory save(VehicleCategory vehicleCategory) {
        try {
            VehicleCategoryEntity vehicleCategoryEntity = vehicleCategoryMapper.fromModelToEntity(vehicleCategory);
            VehicleCategoryEntity saved = vehicleCategoryRepository.save(vehicleCategoryEntity);
            validateSavedEntity(saved);
            return vehicleCategoryMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             vehicleCategoryRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public VehicleCategory findById(Long id) {
        Optional<VehicleCategoryEntity> buVehicleCategory = vehicleCategoryRepository.findById(id);
        if (buVehicleCategory.isPresent()) {
            return vehicleCategoryMapper.fromEntityToModel(buVehicleCategory.get());
        }
        return null;
    }

    @Override
    public List<VehicleCategory> findAll() {
        List<VehicleCategoryEntity> all = vehicleCategoryRepository.findAll();
        return vehicleCategoryMapper.map(all);
    }

    @Override
    public VehicleCategory update(Long id, VehicleCategory vehicleCategory) {
        Optional<VehicleCategoryEntity> resultById = vehicleCategoryRepository.findById(id);
        if (resultById.isPresent()) {
            VehicleCategoryEntity vehicleCategoryToChange = resultById.get();
            vehicleCategoryToChange.update(id, vehicleCategoryToChange);

            return vehicleCategoryMapper.fromEntityToModel(vehicleCategoryRepository.save(vehicleCategoryToChange));
        }
        return null;
    }

    private void validateSavedEntity(VehicleCategoryEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }

        if (saved.getName() == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: nome do produto é nulo");
        }
    }
}