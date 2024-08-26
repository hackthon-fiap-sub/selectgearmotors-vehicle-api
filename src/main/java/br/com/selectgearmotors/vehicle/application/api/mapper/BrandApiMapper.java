package br.com.selectgearmotors.vehicle.application.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.BrandRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.BrandResponse;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandApiMapper {

    @Mapping(source = "name", target = "name")
    Brand fromRequest(BrandRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    BrandResponse fromEntity(Brand brand);

    List<BrandResponse> map(List<Brand> brands);
}
