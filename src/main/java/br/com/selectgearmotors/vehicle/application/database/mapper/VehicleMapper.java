package br.com.selectgearmotors.vehicle.application.database.mapper;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "cor", target = "cor")
    @Mapping(source = "pic", target = "pic")
    @Mapping(source = "year", target = "year")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "vehicleTypeId", target = "vehicleTypeEntity.id")
    @Mapping(source = "brandId", target = "brandEntity.id")
    @Mapping(source = "modelId", target = "modelEntity.id")
    @Mapping(source = "vehicleStatus", target = "vehicleStatus")
    VehicleEntity fromModelTpEntity(Vehicle vehicle);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "vehicleTypeId", source = "vehicleTypeEntity.id")
    @Mapping(target = "brandId", source = "brandEntity.id")
    @Mapping(target = "modelId", source = "brandEntity.id")
    Vehicle fromEntityToModel(VehicleEntity vehicleEntity);

    List<Vehicle> map(List<VehicleEntity> vehicleEntities);
}