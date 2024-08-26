package br.com.selectgearmotors.vehicle.core.ports.in.vehicletype;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;

import java.util.List;

public interface FindVehicleTypesPort {
    List<VehicleType> findAll();
}
