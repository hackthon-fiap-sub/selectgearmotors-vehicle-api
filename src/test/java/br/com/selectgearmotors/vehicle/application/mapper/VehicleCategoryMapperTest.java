package br.com.selectgearmotors.vehicle.application.mapper;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleCategoryMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleCategoryMapperTest {

    private VehicleCategoryMapper mapper = Mappers.getMapper(VehicleCategoryMapper.class);

    @Test
    public void testFromModelTpEntity() {
        // Arrange
        VehicleCategory vehicleCategory = new VehicleCategory();
        vehicleCategory.setId(1L);
        vehicleCategory.setName("SUV");

        // Act
        VehicleCategoryEntity entity = mapper.fromModelToEntity(vehicleCategory);

        // Assert
        assertNotNull(entity);
        assertEquals(vehicleCategory.getId(), entity.getId());
        assertEquals(vehicleCategory.getName(), entity.getName());
    }

    @Test
    public void testFromEntityToModel() {
        // Arrange
        VehicleCategoryEntity entity = new VehicleCategoryEntity();
        entity.setId(1L);
        entity.setName("SUV");

        // Act
        VehicleCategory vehicleCategory = mapper.fromEntityToModel(entity);

        // Assert
        assertNotNull(vehicleCategory);
        assertEquals(entity.getId(), vehicleCategory.getId());
        assertEquals(entity.getName(), vehicleCategory.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        VehicleCategoryEntity entity1 = new VehicleCategoryEntity();
        entity1.setId(1L);
        entity1.setName("SUV");

        VehicleCategoryEntity entity2 = new VehicleCategoryEntity();
        entity2.setId(2L);
        entity2.setName("Sedan");

        List<VehicleCategoryEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);

        // Act
        List<VehicleCategory> vehicleCategoryList = mapper.map(entityList);

        // Assert
        assertNotNull(vehicleCategoryList);
        assertEquals(2, vehicleCategoryList.size());
        assertEquals(entity1.getId(), vehicleCategoryList.get(0).getId());
        assertEquals(entity1.getName(), vehicleCategoryList.get(0).getName());
        assertEquals(entity2.getId(), vehicleCategoryList.get(1).getId());
        assertEquals(entity2.getName(), vehicleCategoryList.get(1).getName());
    }
}