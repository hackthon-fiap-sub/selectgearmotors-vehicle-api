package br.com.selectgearmotors.vehicle.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.ModelRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.ModelResponse;
import br.com.selectgearmotors.vehicle.application.api.mapper.ModelApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelApiMapperTest {

    private ModelApiMapper mapper = Mappers.getMapper(ModelApiMapper.class);

    @Test
    public void testFromRequest() {
        // Arrange
        ModelRequest request = new ModelRequest();
        request.setName("Test Model");

        // Act
        Model model = mapper.fromRequest(request);

        // Assert
        assertNotNull(model);
        assertEquals(request.getName(), model.getName());
    }

    @Test
    public void testFromEntity() {
        // Arrange
        Model model = new Model();
        model.setId(1L);
        model.setName("Test Model");

        // Act
        ModelResponse response = mapper.fromEntity(model);

        // Assert
        assertNotNull(response);
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getName(), response.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        Model model1 = new Model();
        model1.setId(1L);
        model1.setName("Test Model 1");

        Model model2 = new Model();
        model2.setId(2L);
        model2.setName("Test Model 2");

        List<Model> modelList = new ArrayList<>();
        modelList.add(model1);
        modelList.add(model2);

        // Act
        List<ModelResponse> responseList = mapper.map(modelList);

        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(model1.getId(), responseList.get(0).getId());
        assertEquals(model1.getName(), responseList.get(0).getName());
        assertEquals(model2.getId(), responseList.get(1).getId());
        assertEquals(model2.getName(), responseList.get(1).getName());
    }
}