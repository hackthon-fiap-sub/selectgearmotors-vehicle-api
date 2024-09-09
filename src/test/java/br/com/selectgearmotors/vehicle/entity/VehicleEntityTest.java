package br.com.selectgearmotors.vehicle.entity;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.MediaType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleFuel;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleEntityTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        VehicleEntity entity = new VehicleEntity(1L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V123", entity.getCode());
        assertEquals("Red", entity.getCor());
        assertEquals(2022, entity.getVehicleYear());
        assertEquals("Description", entity.getDescription());
        assertEquals(new BigDecimal("10000"), entity.getPrice());
        assertNotNull(entity.getVehicleCategoryEntity());
        assertNotNull(entity.getModelEntity());
        assertEquals(VehicleStatus.AVAILABLE, entity.getVehicleStatus());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        VehicleEntity entity = new VehicleEntity();

        // Act
        entity.setId(1L);
        entity.setCode("V123");
        entity.setCor("Red");
        entity.setVehicleYear(2022);
        entity.setDescription("Description");
        entity.setPrice(new BigDecimal("10000"));
        entity.setVehicleCategoryEntity(new VehicleCategoryEntity());
        entity.setModelEntity(new ModelEntity());
        entity.setVehicleStatus(VehicleStatus.AVAILABLE);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V123", entity.getCode());
        assertEquals("Red", entity.getCor());
        assertEquals(2022, entity.getVehicleYear());
        assertEquals("Description", entity.getDescription());
        assertEquals(new BigDecimal("10000"), entity.getPrice());
        assertNotNull(entity.getVehicleCategoryEntity());
        assertNotNull(entity.getModelEntity());
        assertEquals(VehicleStatus.AVAILABLE, entity.getVehicleStatus());
    }

    @Test
    public void testToString() {
        // Arrange
        VehicleEntity entity = new VehicleEntity(1L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");
        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("VehicleEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("code=V123"));
        assertTrue(toString.contains("cor=Red"));
        assertTrue(toString.contains("vehicleYear=2022"));
        assertTrue(toString.contains("description=Description"));
        assertTrue(toString.contains("price=10000"));
        assertTrue(toString.contains("vehicleStatus=AVAILABLE"));
    }

    @Disabled
    public void testHashCode() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity(1L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");
        VehicleEntity entity2 = new VehicleEntity(1L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");
        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity(1L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");
        VehicleEntity entity2 = new VehicleEntity(1L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");
        VehicleEntity entity3 = new VehicleEntity(2L, "V123",  "Red", 2022, 1233, "Description", new BigDecimal("10000"), new VehicleCategoryEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE, VehicleType.CAR, VehicleFuel.FLEX, new MediaEntity(1L, "pic.jpg", "image/jpeg", MediaType.JPG), "Uberlândia");

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}