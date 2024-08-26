package br.com.selectgearmotors.vehicle.application.mapper;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleTypeMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeMapperTest {

    private VehicleTypeMapper mapper = Mappers.getMapper(VehicleTypeMapper.class);

    @Test
    public void testFromModelTpEntity() {
        // Arrange
        VehicleType vehicleType = new VehicleType();
        vehicleType.setId(1L);
        vehicleType.setName("SUV");

        // Act
        VehicleTypeEntity entity = mapper.fromModelTpEntity(vehicleType);

        // Assert
        assertNotNull(entity);
        assertEquals(vehicleType.getId(), entity.getId());
        assertEquals(vehicleType.getName(), entity.getName());
    }

    @Test
    public void testFromEntityToModel() {
        // Arrange
        VehicleTypeEntity entity = new VehicleTypeEntity();
        entity.setId(1L);
        entity.setName("SUV");

        // Act
        VehicleType vehicleType = mapper.fromEntityToModel(entity);

        // Assert
        assertNotNull(vehicleType);
        assertEquals(entity.getId(), vehicleType.getId());
        assertEquals(entity.getName(), vehicleType.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        VehicleTypeEntity entity1 = new VehicleTypeEntity();
        entity1.setId(1L);
        entity1.setName("SUV");

        VehicleTypeEntity entity2 = new VehicleTypeEntity();
        entity2.setId(2L);
        entity2.setName("Sedan");

        List<VehicleTypeEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);

        // Act
        List<VehicleType> vehicleTypeList = mapper.map(entityList);

        // Assert
        assertNotNull(vehicleTypeList);
        assertEquals(2, vehicleTypeList.size());
        assertEquals(entity1.getId(), vehicleTypeList.get(0).getId());
        assertEquals(entity1.getName(), vehicleTypeList.get(0).getName());
        assertEquals(entity2.getId(), vehicleTypeList.get(1).getId());
        assertEquals(entity2.getName(), vehicleTypeList.get(1).getName());
    }
}