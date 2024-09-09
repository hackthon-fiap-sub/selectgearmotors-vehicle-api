package br.com.selectgearmotors.vehicle.core.ports.out;

import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;

import java.util.List;

public interface VehicleCategoryRepositoryPort {
    VehicleCategory save(VehicleCategory vehicleCategory);
    boolean remove(Long id);
    VehicleCategory findById(Long id);
    List<VehicleCategory> findAll();
    VehicleCategory update(Long id, VehicleCategory vehicleCategory);
}
