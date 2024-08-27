package br.com.selectgearmotors.vehicle.application.database.mapper;

import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "brandId", target = "brandEntity.id")
    ModelEntity fromModelToEntity(Model brand);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "brandId", source = "brandEntity.id")
    Model fromEntityToModel(ModelEntity brandEntity);
    List<Model> map(List<ModelEntity> brandEntities);
}
