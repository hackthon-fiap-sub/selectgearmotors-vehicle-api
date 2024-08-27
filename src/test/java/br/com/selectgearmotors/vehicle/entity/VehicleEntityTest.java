package br.com.selectgearmotors.vehicle.entity;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleEntityTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        VehicleEntity entity = new VehicleEntity(1L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V123", entity.getCode());
        assertEquals("Red", entity.getCor());
        assertEquals("pic.jpg", entity.getPic());
        assertEquals(2022, entity.getYear());
        assertEquals("Description", entity.getDescription());
        assertEquals(new BigDecimal("10000"), entity.getPrice());
        assertNotNull(entity.getVehicleTypeEntity());
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
        entity.setPic("pic.jpg");
        entity.setYear(2022);
        entity.setDescription("Description");
        entity.setPrice(new BigDecimal("10000"));
        entity.setVehicleTypeEntity(new VehicleTypeEntity());
        entity.setModelEntity(new ModelEntity());
        entity.setVehicleStatus(VehicleStatus.AVAILABLE);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V123", entity.getCode());
        assertEquals("Red", entity.getCor());
        assertEquals("pic.jpg", entity.getPic());
        assertEquals(2022, entity.getYear());
        assertEquals("Description", entity.getDescription());
        assertEquals(new BigDecimal("10000"), entity.getPrice());
        assertNotNull(entity.getVehicleTypeEntity());
        assertNotNull(entity.getModelEntity());
        assertEquals(VehicleStatus.AVAILABLE, entity.getVehicleStatus());
    }

    @Test
    public void testToString() {
        // Arrange
        VehicleEntity entity = new VehicleEntity(1L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("VehicleEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("code=V123"));
        assertTrue(toString.contains("cor=Red"));
        assertTrue(toString.contains("pic=pic.jpg"));
        assertTrue(toString.contains("year=2022"));
        assertTrue(toString.contains("description=Description"));
        assertTrue(toString.contains("price=10000"));
        assertTrue(toString.contains("vehicleStatus=ACTIVE"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity(1L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);
        VehicleEntity entity2 = new VehicleEntity(1L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        VehicleEntity entity1 = new VehicleEntity(1L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);
        VehicleEntity entity2 = new VehicleEntity(1L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);
        VehicleEntity entity3 = new VehicleEntity(2L, "V123", "Red", "pic.jpg", 2022, "Description", new BigDecimal("10000"), new VehicleTypeEntity(1L, "Automotor"), new ModelEntity(1L, "Flex", new BrandEntity(1L, "Ford")), VehicleStatus.AVAILABLE);

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}