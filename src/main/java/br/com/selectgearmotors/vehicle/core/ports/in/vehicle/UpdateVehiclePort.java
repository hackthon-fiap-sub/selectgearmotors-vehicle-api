package br.com.selectgearmotors.vehicle.core.ports.in.vehicle;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;

public interface UpdateVehiclePort {
    Vehicle update(Long id, Vehicle vehicle);
    void updateReserved(String code);
    void updateSold(String code);
}
