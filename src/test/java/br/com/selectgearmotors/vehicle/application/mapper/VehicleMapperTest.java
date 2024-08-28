package br.com.selectgearmotors.vehicle.application.mapper;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleMapper;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleMapperTest {

    private VehicleMapper mapper = Mappers.getMapper(VehicleMapper.class);

    @Test
    public void testFromModelTpEntity() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setCode("V123");
        vehicle.setCor("Red");
        vehicle.setDescription("Description");
        vehicle.setPrice(new BigDecimal("10000"));
        vehicle.setPic("pic.jpg");
        vehicle.setVehicleTypeId(1L);
        vehicle.setModelId(3L);
        //vehicle.setVehicleStatus("AVAILABLE");

        // Act
        VehicleEntity entity = mapper.fromModelTpEntity(vehicle);

        // Assert
        assertNotNull(entity);
        assertEquals(vehicle.getId(), entity.getId());
        assertEquals(vehicle.getCode(), entity.getCode());
        assertEquals(vehicle.getCor(), entity.getCor());
        assertEquals(vehicle.getDescription(), entity.getDescription());
        assertEquals(vehicle.getPrice(), entity.getPrice());
        assertEquals(vehicle.getPic(), entity.getPic());
        assertEquals(vehicle.getVehicleTypeId(), entity.getVehicleTypeEntity().getId());
        assertEquals(vehicle.getModelId(), entity.getModelEntity().getId());
       // assertEquals(vehicle.getVehicleStatus(), entity.getVehicleStatus());
    }

    @Test
    public void testFromEntityToModel() {
        // Arrange
        VehicleEntity entity = new VehicleEntity();
        entity.setId(1L);
        entity.setCode("V123");
        entity.setCor("Red");
        entity.setDescription("Description");
        entity.setPrice(new BigDecimal("10000"));
        entity.setPic("pic.jpg");
        entity.setVehicleTypeEntity(new VehicleTypeEntity(1L, "Type"));
        entity.setModelEntity(new ModelEntity(3L, "Model", new BrandEntity(2L, "Brand")));
        entity.setVehicleStatus(VehicleStatus.AVAILABLE);

        // Act
        Vehicle vehicle = mapper.fromEntityToModel(entity);

        // Assert
        assertNotNull(vehicle);
        assertEquals(entity.getId(), vehicle.getId());
        assertEquals(entity.getCode(), vehicle.getCode());
        assertEquals(entity.getCor(), vehicle.getCor());
        assertEquals(entity.getDescription(), vehicle.getDescription());
        assertEquals(entity.getPrice(), vehicle.getPrice());
        assertEquals(entity.getPic(), vehicle.getPic());
        assertEquals(entity.getVehicleTypeEntity().getId(), vehicle.getVehicleTypeId());
        assertEquals(entity.getModelEntity().getId(), vehicle.getModelId());
        assertEquals(entity.getVehicleStatus().name(), vehicle.getVehicleStatus());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity();
        entity1.setId(1L);
        entity1.setCode("V123");
        entity1.setCor("Red");
        entity1.setDescription("Description1");
        entity1.setPrice(new BigDecimal("10000"));
        entity1.setPic("pic1.jpg");
        entity1.setVehicleTypeEntity(new VehicleTypeEntity(1L, "Type1"));
        entity1.setModelEntity(new ModelEntity(3L, "Model1", new BrandEntity(2L, "Brand1")));
        entity1.setVehicleStatus(VehicleStatus.AVAILABLE);

        VehicleEntity entity2 = new VehicleEntity();
        entity2.setId(2L);
        entity2.setCode("V124");
        entity2.setCor("Blue");
        entity2.setDescription("Description2");
        entity2.setPrice(new BigDecimal("20000"));
        entity2.setPic("pic2.jpg");
        entity2.setVehicleTypeEntity(new VehicleTypeEntity(4L, "Type2"));
        entity2.setModelEntity(new ModelEntity(6L, "Model2", new BrandEntity(5L, "Brand2")));
        entity2.setVehicleStatus(VehicleStatus.RESERVED);

        List<VehicleEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);

        // Act
        List<Vehicle> vehicleList = mapper.map(entityList);

        // Assert
        assertNotNull(vehicleList);
        assertEquals(2, vehicleList.size());
        assertEquals(entity1.getId(), vehicleList.get(0).getId());
        assertEquals(entity1.getCode(), vehicleList.get(0).getCode());
        assertEquals(entity1.getCor(), vehicleList.get(0).getCor());
        assertEquals(entity1.getDescription(), vehicleList.get(0).getDescription());
        assertEquals(entity1.getPrice(), vehicleList.get(0).getPrice());
        assertEquals(entity1.getPic(), vehicleList.get(0).getPic());
        assertEquals(entity1.getVehicleTypeEntity().getId(), vehicleList.get(0).getVehicleTypeId());
        assertEquals(entity1.getModelEntity().getId(), vehicleList.get(0).getModelId());
        assertEquals(entity1.getVehicleStatus().name(), vehicleList.get(0).getVehicleStatus());

        assertEquals(entity2.getId(), vehicleList.get(1).getId());
        assertEquals(entity2.getCode(), vehicleList.get(1).getCode());
        assertEquals(entity2.getCor(), vehicleList.get(1).getCor());
        assertEquals(entity2.getDescription(), vehicleList.get(1).getDescription());
        assertEquals(entity2.getPrice(), vehicleList.get(1).getPrice());
        assertEquals(entity2.getPic(), vehicleList.get(1).getPic());
        assertEquals(entity2.getVehicleTypeEntity().getId(), vehicleList.get(1).getVehicleTypeId());
        assertEquals(entity2.getModelEntity().getId(), vehicleList.get(1).getModelId());
        assertEquals(entity2.getVehicleStatus().name(), vehicleList.get(1).getVehicleStatus());
    }
}