package br.com.selectgearmotors.vehicle.domain;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        Vehicle vehicle = new Vehicle(1L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L, 3L, "ACTIVE");

        // Assert
        assertEquals(1L, vehicle.getId());
        assertEquals("123", vehicle.getCode());
        assertEquals("Azul", vehicle.getCor());
        assertEquals("Description", vehicle.getDescription());
        assertEquals(new BigDecimal("10000"), vehicle.getPrice());
        assertEquals("pic.jpg", vehicle.getPic());
        assertEquals(1L, vehicle.getVehicleTypeId());
        assertEquals(3L, vehicle.getModelId());
        assertEquals("ACTIVE", vehicle.getVehicleStatus());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Vehicle vehicle = new Vehicle();

        // Act
        vehicle.setId(1L);
        vehicle.setCode("V123");
        vehicle.setCor("VehicleName");
        vehicle.setDescription("Description");
        vehicle.setPrice(new BigDecimal("10000"));
        vehicle.setPic("pic.jpg");
        vehicle.setVehicleTypeId(1L);
        vehicle.setModelId(3L);
        vehicle.setVehicleStatus("ACTIVE");

        // Assert
        assertEquals(1L, vehicle.getId());
        assertEquals("V123", vehicle.getCode());
        assertEquals("VehicleName", vehicle.getCor());
        assertEquals("Description", vehicle.getDescription());
        assertEquals(new BigDecimal("10000"), vehicle.getPrice());
        assertEquals("pic.jpg", vehicle.getPic());
        assertEquals(1L, vehicle.getVehicleTypeId());
        assertEquals(3L, vehicle.getModelId());
        assertEquals("ACTIVE", vehicle.getVehicleStatus());
    }

    @Test
    public void testToString() {
        // Arrange
        Vehicle vehicle = new Vehicle(1L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L, 3L, "ACTIVE");

        // Act
        String toString = vehicle.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Vehicle"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("code=123"));
        assertTrue(toString.contains("cor=Azul"));
        assertTrue(toString.contains("description=Description"));
        assertTrue(toString.contains("price=10000"));
        assertTrue(toString.contains("pic=pic.jpg"));
        assertTrue(toString.contains("vehicleTypeId=1"));
        assertTrue(toString.contains("brandId=2"));
        assertTrue(toString.contains("modelId=3"));
        assertTrue(toString.contains("vehicleStatus=ACTIVE"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        Vehicle vehicle1 = new Vehicle(1L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L,  3L, "ACTIVE");
        Vehicle vehicle2 = new Vehicle(1L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L, 3L, "ACTIVE");

        // Act & Assert
        assertEquals(vehicle1.hashCode(), vehicle2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        Vehicle vehicle1 = new Vehicle(1L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L,  3L, "ACTIVE");
        Vehicle vehicle2 = new Vehicle(1L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L,  3L, "ACTIVE");
        Vehicle vehicle3 = new Vehicle(2L, "123", "Azul", 2024, "Description", new BigDecimal("10000"), "pic.jpg", 1L,  3L, "ACTIVE");

        // Act & Assert
        assertEquals(vehicle1, vehicle2);
        assertNotEquals(vehicle1, vehicle3);
    }
}