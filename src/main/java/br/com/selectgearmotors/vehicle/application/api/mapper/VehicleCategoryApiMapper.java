package br.com.selectgearmotors.vehicle.application.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleCategoryRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleCategoryResponse;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleCategoryApiMapper {

    @Mapping(source = "name", target = "name")
    VehicleCategory fromRequest(VehicleCategoryRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    VehicleCategoryResponse fromEntity(VehicleCategory vehicleCategory);

    List<VehicleCategoryResponse> map(List<VehicleCategory> vehicleCategories);
}
