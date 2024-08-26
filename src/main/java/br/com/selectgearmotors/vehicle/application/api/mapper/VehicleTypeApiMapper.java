package br.com.selectgearmotors.vehicle.application.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleTypeRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleTypeResponse;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleTypeApiMapper {

    @Mapping(source = "name", target = "name")
    VehicleType fromRequest(VehicleTypeRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    VehicleTypeResponse fromEntity(VehicleType vehicleType);

    List<VehicleTypeResponse> map(List<VehicleType> vehicleTypes);
}
