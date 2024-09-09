package br.com.selectgearmotors.vehicle.entity;

import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleCategoryEntityTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        VehicleCategoryEntity entity = new VehicleCategoryEntity(1L, "V$");

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V$", entity.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        VehicleCategoryEntity entity = new VehicleCategoryEntity();

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
        VehicleCategoryEntity entity = new VehicleCategoryEntity(1L, "V$");

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=V$"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        VehicleCategoryEntity entity1 = new VehicleCategoryEntity(1L, "V$");
        VehicleCategoryEntity entity2 = new VehicleCategoryEntity(1L, "V$");

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        VehicleCategoryEntity entity1 = new VehicleCategoryEntity(1L, "V$");
        VehicleCategoryEntity entity2 = new VehicleCategoryEntity(1L, "V$");
        VehicleCategoryEntity entity3 = new VehicleCategoryEntity(2L, "X$");

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}