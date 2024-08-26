package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleTypeMapper;
import br.com.selectgearmotors.vehicle.commons.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleTypeRepositoryPort;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleTypeRepositoryAdapter implements VehicleTypeRepositoryPort {

    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleTypeMapper vehicleTypeMapper;

    @Override
    public VehicleType save(VehicleType vehicleType) {
        try {
            VehicleTypeEntity vehicleTypeEntity = vehicleTypeMapper.fromModelTpEntity(vehicleType);
            VehicleTypeEntity saved = vehicleTypeRepository.save(vehicleTypeEntity);
            validateSavedEntity(saved);
            return vehicleTypeMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             vehicleTypeRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public VehicleType findById(Long id) {
        Optional<VehicleTypeEntity> buVehicleType = vehicleTypeRepository.findById(id);
        if (buVehicleType.isPresent()) {
            return vehicleTypeMapper.fromEntityToModel(buVehicleType.get());
        }
        return null;
    }

    @Override
    public List<VehicleType> findAll() {
        List<VehicleTypeEntity> all = vehicleTypeRepository.findAll();
        return vehicleTypeMapper.map(all);
    }

    @Override
    public VehicleType update(Long id, VehicleType vehicleType) {
        Optional<VehicleTypeEntity> resultById = vehicleTypeRepository.findById(id);
        if (resultById.isPresent()) {
            VehicleTypeEntity vehicleTypeToChange = resultById.get();
            vehicleTypeToChange.update(id, vehicleTypeToChange);

            return vehicleTypeMapper.fromEntityToModel(vehicleTypeRepository.save(vehicleTypeToChange));
        }
        return null;
    }

    private void validateSavedEntity(VehicleTypeEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }

        if (saved.getName() == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: nome do produto é nulo");
        }
    }
}