package br.com.selectgearmotors.vehicle.domain;

import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleCategoryTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        VehicleCategory vehicleCategory = new VehicleCategory(1L, "VehicleTypeName");

        // Assert
        assertEquals(1L, vehicleCategory.getId());
        assertEquals("VehicleTypeName", vehicleCategory.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        VehicleCategory vehicleCategory = new VehicleCategory();

        // Act
        vehicleCategory.setId(1L);
        vehicleCategory.setName("VehicleTypeName");

        // Assert
        assertEquals(1L, vehicleCategory.getId());
        assertEquals("VehicleTypeName", vehicleCategory.getName());
    }

    @Test
    public void testToString() {
        // Arrange
        VehicleCategory vehicleCategory = new VehicleCategory(1L, "VehicleTypeName");

        // Act
        String toString = vehicleCategory.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("VehicleType"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=VehicleTypeName"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        VehicleCategory vehicleCategory1 = new VehicleCategory(1L, "VehicleTypeName");
        VehicleCategory vehicleCategory2 = new VehicleCategory(1L, "VehicleTypeName");

        // Act & Assert
        assertEquals(vehicleCategory1.hashCode(), vehicleCategory2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        VehicleCategory vehicleCategory1 = new VehicleCategory(1L, "VehicleTypeName");
        VehicleCategory vehicleCategory2 = new VehicleCategory(1L, "VehicleTypeName");
        VehicleCategory vehicleCategory3 = new VehicleCategory(2L, "AnotherVehicleType");

        // Act & Assert
        assertEquals(vehicleCategory1, vehicleCategory2);
        assertNotEquals(vehicleCategory1, vehicleCategory3);
    }
}