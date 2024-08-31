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
    @Mapping(source = "vehicleYear", target = "vehicleYear")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "vehicleTypeId", target = "vehicleTypeEntity.id")
    @Mapping(source = "modelId", target = "modelEntity.id")
    @Mapping(source = "vehicleStatus", target = "vehicleStatus")
    VehicleEntity fromModelTpEntity(Vehicle vehicle);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "vehicleTypeId", source = "vehicleTypeEntity.id")
    @Mapping(target = "vehicleTypeName", source = "vehicleTypeEntity.name")
    @Mapping(target = "modelId", source = "modelEntity.id")
    @Mapping(target = "modelName", source = "modelEntity.name")
    @Mapping(target = "brandId", source = "modelEntity.brandEntity.id")
    @Mapping(target = "brandName", source = "modelEntity.brandEntity.name")
    Vehicle fromEntityToModel(VehicleEntity vehicleEntity);

    List<Vehicle> map(List<VehicleEntity> vehicleEntities);
}