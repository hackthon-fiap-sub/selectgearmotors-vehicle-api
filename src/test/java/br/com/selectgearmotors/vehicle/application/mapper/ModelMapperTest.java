package br.com.selectgearmotors.vehicle.application.mapper;

import br.com.selectgearmotors.vehicle.application.database.mapper.ModelMapper;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperTest {

    private ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    @Test
    public void testFromModelToEntity() {
        // Arrange
        Model model = new Model();
        model.setId(1L);
        model.setName("Test Model");

        // Act
        ModelEntity entity = mapper.fromModelToEntity(model);

        // Assert
        assertNotNull(entity);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
    }

    @Test
    public void testFromEntityToModel() {
        // Arrange
        ModelEntity entity = new ModelEntity();
        entity.setId(1L);
        entity.setName("Test Model");

        // Act
        Model model = mapper.fromEntityToModel(entity);

        // Assert
        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        ModelEntity entity1 = new ModelEntity();
        entity1.setId(1L);
        entity1.setName("Test Model 1");

        ModelEntity entity2 = new ModelEntity();
        entity2.setId(2L);
        entity2.setName("Test Model 2");

        List<ModelEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);

        // Act
        List<Model> modelList = mapper.map(entityList);

        // Assert
        assertNotNull(modelList);
        assertEquals(2, modelList.size());
        assertEquals(entity1.getId(), modelList.get(0).getId());
        assertEquals(entity1.getName(), modelList.get(0).getName());
        assertEquals(entity2.getId(), modelList.get(1).getId());
        assertEquals(entity2.getName(), modelList.get(1).getName());
    }
}