package br.com.selectgearmotors.vehicle.core.ports.out;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;

import java.util.List;

public interface VehicleTypeRepositoryPort {
    VehicleType save(VehicleType vehicleType);
    boolean remove(Long id);
    VehicleType findById(Long id);
    List<VehicleType> findAll();
    VehicleType update(Long id, VehicleType vehicleType);
}
