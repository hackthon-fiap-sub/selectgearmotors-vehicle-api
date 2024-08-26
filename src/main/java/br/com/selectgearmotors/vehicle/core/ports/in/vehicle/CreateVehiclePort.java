package br.com.selectgearmotors.vehicle.core.ports.in.vehicle;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;

public interface CreateVehiclePort {
    Vehicle save(Vehicle vehicle);
}
