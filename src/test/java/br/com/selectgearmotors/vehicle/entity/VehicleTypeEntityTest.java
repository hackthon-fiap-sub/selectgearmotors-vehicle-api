package br.com.selectgearmotors.vehicle.entity;

import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeEntityTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        VehicleTypeEntity entity = new VehicleTypeEntity(1L, "V$");

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V$", entity.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        VehicleTypeEntity entity = new VehicleTypeEntity();

        // Act
        entity.setId(1L);
        entity.setName("V$");

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V$", entity.getName());
    }

    @Test
    public void testToString() {
        // Arrange
        VehicleTypeEntity entity = new VehicleTypeEntity(1L, "V$");

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("VehicleTypeEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=V$"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        VehicleTypeEntity entity1 = new VehicleTypeEntity(1L, "V$");
        VehicleTypeEntity entity2 = new VehicleTypeEntity(1L, "V$");

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        VehicleTypeEntity entity1 = new VehicleTypeEntity(1L, "V$");
        VehicleTypeEntity entity2 = new VehicleTypeEntity(1L, "V$");
        VehicleTypeEntity entity3 = new VehicleTypeEntity(2L, "X$");

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}