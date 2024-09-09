package br.com.selectgearmotors.vehicle.core.ports.in.vehiclecategory;

import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;

public interface UpdateVehicleCategoryPort {
    VehicleCategory update(Long id, VehicleCategory vehicleCategory);
}
