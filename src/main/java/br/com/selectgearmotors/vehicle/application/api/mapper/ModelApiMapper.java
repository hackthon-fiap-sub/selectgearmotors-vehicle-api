package br.com.selectgearmotors.vehicle.application.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.ModelRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.ModelResponse;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelApiMapper {

    @Mapping(source = "name", target = "name")
    Model fromRequest(ModelRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ModelResponse fromEntity(Model model);

    List<ModelResponse> map(List<Model> models);
}
