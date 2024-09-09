package br.com.selectgearmotors.vehicle.application.database.mapper;

import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleCategoryMapper {

    @Mapping(source = "name", target = "name")
    VehicleCategoryEntity fromModelToEntity(VehicleCategory vehicleCategory);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    VehicleCategory fromEntityToModel(VehicleCategoryEntity vehicleCategoryEntity);

    List<VehicleCategory> map(List<VehicleCategoryEntity> vehicleTypeEntities);
}
