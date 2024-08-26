package br.com.selectgearmotors.vehicle.core.ports.in.vehicletype;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;

public interface CreateVehicleTypePort {
    VehicleType save(VehicleType vehicleType);
}
