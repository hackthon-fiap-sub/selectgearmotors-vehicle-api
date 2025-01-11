package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleMapper;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleRepositoryPort;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleRepositoryAdapter implements VehicleRepositoryPort {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public Vehicle save(Vehicle vehicle) {
        try {
            VehicleEntity vehicleEntity = vehicleMapper.fromModelToEntity(vehicle);
            if (vehicleEntity != null) {
                vehicleEntity.setCode(UUID.randomUUID().toString());
                VehicleEntity saved = vehicleRepository.save(vehicleEntity);
                return vehicleMapper.fromEntityToModel(saved);
            }
        } catch (Exception e) {
            log.info("Erro ao salvar produto: " + e.getMessage());
            return null;
        }

        return null;
    }

    @Override
    public boolean remove(Long id) {
        try {
            vehicleRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Vehicle findById(Long id) {
        Optional<VehicleEntity> buVehicle = vehicleRepository.findById(id);
        return buVehicle.map(vehicleMapper::fromEntityToModel).orElse(null);
    }

    @Override
    public List<Vehicle> findAll() {
        List<VehicleEntity> all = vehicleRepository.findAll();
        return vehicleMapper.map(all);
    }

    @Override
    public Vehicle update(Long id, Vehicle vehicle) {
        Optional<VehicleEntity> resultById = vehicleRepository.findById(id);
        if (resultById.isPresent()) {

            VehicleEntity vehicleToChange = resultById.get();
            vehicleToChange.update(id, vehicleToChange);

            return vehicleMapper.fromEntityToModel(vehicleRepository.save(vehicleToChange));
        }

        return null;
    }

    @Override
    public void updateReserved(String code) {
        vehicleRepository.updateStatus(VehicleStatus.RESERVED, code);
    }

    @Override
    public void updateSold(String code) {
        vehicleRepository.updateStatus(VehicleStatus.SOLD, code);
    }

    @Override
    public Vehicle findByCode(String code) {
        VehicleEntity byCode = vehicleRepository.findByCode(code);
        return vehicleMapper.fromEntityToModel(byCode);
    }


}
