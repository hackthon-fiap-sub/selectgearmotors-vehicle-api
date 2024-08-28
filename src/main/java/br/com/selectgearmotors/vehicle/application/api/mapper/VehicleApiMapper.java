package br.com.selectgearmotors.vehicle.application.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleResponse;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleApiMapper {

    @Mapping(source = "cor", target = "cor")
    @Mapping(source = "pic", target = "pic")
    @Mapping(source = "vehicleYear", target = "vehicleYear")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "vehicleTypeId", target = "vehicleTypeId")
    @Mapping(source = "modelId", target = "modelId")
    @Mapping(source = "vehicleStatus", target = "vehicleStatus")
    Vehicle fromRequest(VehicleRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    VehicleResponse fromEntity(Vehicle product);

   List<VehicleResponse> map(List<Vehicle> products);

}
