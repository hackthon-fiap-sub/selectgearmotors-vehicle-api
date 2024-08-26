package br.com.selectgearmotors.vehicle.application.database.mapper;

import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(source = "name", target = "name")
    BrandEntity fromModelToEntity(Brand brand);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    Brand fromEntityToModel(BrandEntity brandEntity);
    List<Brand> map(List<BrandEntity> brandEntities);
}
