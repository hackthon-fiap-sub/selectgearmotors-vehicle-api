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
    @Mapping(source = "mediaId", target = "mediaId")
    @Mapping(source = "vehicleYear", target = "vehicleYear")
    @Mapping(source = "km", target = "km")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "vehicleCategoryId", target = "vehicleCategoryId")
    @Mapping(source = "modelId", target = "modelId")
    @Mapping(source = "vehicleStatus", target = "vehicleStatus")
    @Mapping(source = "plate", target = "plate")
    @Mapping(source = "chassis", target = "chassis")
    @Mapping(source = "renavam", target = "renavam")
    Vehicle fromRequest(VehicleRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "vehicleCategoryId", source = "vehicleCategoryId")
    @Mapping(target = "vehicleCategoryName", source = "vehicleCategoryName")
    @Mapping(target = "modelId", source = "modelId")
    @Mapping(target = "modelName", source = "modelName")
    @Mapping(target = "brandId", source = "brandId")
    @Mapping(target = "brandName", source = "brandName")
    VehicleResponse fromEntity(Vehicle product);

    List<VehicleResponse> map(List<Vehicle> products);
}
