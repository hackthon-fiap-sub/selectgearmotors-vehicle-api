package br.com.selectgearmotors.vehicle.core.ports.in.vehicle;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;

import java.util.List;

public interface FindVehiclesPort {
    List<Vehicle> findAll();
}
