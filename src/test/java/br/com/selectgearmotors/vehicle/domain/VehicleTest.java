package br.com.selectgearmotors.vehicle.domain;

import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

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
}