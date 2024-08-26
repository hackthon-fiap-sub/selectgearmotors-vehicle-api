package br.com.selectgearmotors.vehicle.core.ports.in.vehicle;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;

public interface FindByIdVehiclePort {
    Vehicle findById(Long id);
    Vehicle findByCode(String code);
}
