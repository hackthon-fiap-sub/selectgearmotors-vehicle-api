package br.com.selectgearmotors.vehicle.core.ports.in.vehicletype;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;

public interface FindByIdVehicleTypePort {
    VehicleType findById(Long id);
}
