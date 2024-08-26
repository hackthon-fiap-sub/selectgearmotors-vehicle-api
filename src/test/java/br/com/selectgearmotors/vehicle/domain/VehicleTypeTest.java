package br.com.selectgearmotors.vehicle.domain;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        VehicleType vehicleType = new VehicleType(1L, "VehicleTypeName");

        // Assert
        assertEquals(1L, vehicleType.getId());
        assertEquals("VehicleTypeName", vehicleType.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        VehicleType vehicleType = new VehicleType();

        // Act
        vehicleType.setId(1L);
        vehicleType.setName("VehicleTypeName");

        // Assert
        assertEquals(1L, vehicleType.getId());
        assertEquals("VehicleTypeName", vehicleType.getName());
    }

    @Test
    public void testToString() {
        // Arrange
        VehicleType vehicleType = new VehicleType(1L, "VehicleTypeName");

        // Act
        String toString = vehicleType.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("VehicleType"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=VehicleTypeName"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        VehicleType vehicleType1 = new VehicleType(1L, "VehicleTypeName");
        VehicleType vehicleType2 = new VehicleType(1L, "VehicleTypeName");

        // Act & Assert
        assertEquals(vehicleType1.hashCode(), vehicleType2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        VehicleType vehicleType1 = new VehicleType(1L, "VehicleTypeName");
        VehicleType vehicleType2 = new VehicleType(1L, "VehicleTypeName");
        VehicleType vehicleType3 = new VehicleType(2L, "AnotherVehicleType");

        // Act & Assert
        assertEquals(vehicleType1, vehicleType2);
        assertNotEquals(vehicleType1, vehicleType3);
    }
}