package br.com.selectgearmotors.vehicle.core.ports.out;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;

import java.util.List;

public interface VehicleRepositoryPort {
    Vehicle save(Vehicle product);
    boolean remove(Long id);
    Vehicle findById(Long id);
    List<Vehicle> findAll();
    Vehicle update(Long id, Vehicle product);
    void updateReserved(String code);
    void updateSold(String code);
    Vehicle findByCode(String code);
}
