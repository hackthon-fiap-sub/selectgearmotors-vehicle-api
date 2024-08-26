package br.com.selectgearmotors.vehicle.application.database.mapper;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleTypeMapper {

    @Mapping(source = "name", target = "name")
    VehicleTypeEntity fromModelTpEntity(VehicleType vehicleType);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    VehicleType fromEntityToModel(VehicleTypeEntity vehicleTypeEntity);

    List<VehicleType> map(List<VehicleTypeEntity> vehicleTypeEntities);
}
