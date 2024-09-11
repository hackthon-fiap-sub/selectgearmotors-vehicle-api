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
    @Mapping(source = "vehicleYear", target = "vehicleYear")
    @Mapping(source = "km", target = "km")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "vehicleCategoryId", target = "vehicleCategoryEntity.id")
    @Mapping(source = "modelId", target = "modelEntity.id")
    @Mapping(source = "vehicleStatus", target = "vehicleStatus")
    @Mapping(source = "vehicleType", target = "vehicleType")
    @Mapping(source = "vehicleFuel", target = "vehicleFuel")
    @Mapping(source = "mediaId", target = "mediaEntity.id")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "plate", target = "plate")
    @Mapping(source = "chassis", target = "chassis")
    @Mapping(source = "renavam", target = "renavam")
    VehicleEntity fromModelToEntity(Vehicle vehicle);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "vehicleCategoryId", source = "vehicleCategoryEntity.id")
    @Mapping(target = "vehicleCategoryName", source = "vehicleCategoryEntity.name")
    @Mapping(target = "modelId", source = "modelEntity.id")
    @Mapping(target = "modelName", source = "modelEntity.name")
    @Mapping(target = "brandId", source = "modelEntity.brandEntity.id")
    @Mapping(target = "brandName", source = "modelEntity.brandEntity.name")
    @Mapping(target = "mediaId", source = "mediaEntity.id")
    Vehicle fromEntityToModel(VehicleEntity vehicleEntity);

    List<Vehicle> map(List<VehicleEntity> vehicleEntities);
}