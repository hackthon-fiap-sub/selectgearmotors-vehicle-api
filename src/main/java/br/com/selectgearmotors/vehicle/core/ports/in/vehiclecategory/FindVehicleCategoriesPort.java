package br.com.selectgearmotors.vehicle.core.ports.in.vehiclecategory;

import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;

import java.util.List;

public interface FindVehicleCategoriesPort {
    List<VehicleCategory> findAll();
}
