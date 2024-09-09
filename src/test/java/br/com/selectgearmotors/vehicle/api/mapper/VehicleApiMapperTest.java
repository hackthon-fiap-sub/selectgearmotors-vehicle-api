package br.com.selectgearmotors.vehicle.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleResponse;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleApiMapperTest {

    private VehicleApiMapper mapper = Mappers.getMapper(VehicleApiMapper.class);

    @Test
    public void testFromRequest() {
        // Arrange
        VehicleRequest request = new VehicleRequest();
        request.setCor("Red");
        request.setVehicleYear(2021);
        request.setDescription("Description");
        request.setPrice(new BigDecimal("10000"));
        request.setVehicleCategoryId(1L);
        request.setModelId(2L);
        request.setVehicleStatus("ACTIVE");

        // Act
        Vehicle vehicle = mapper.fromRequest(request);

        // Assert
        assertNotNull(vehicle);
        assertEquals(request.getCor(), vehicle.getCor());
        assertEquals(request.getVehicleYear(), vehicle.getVehicleYear());
        assertEquals(request.getDescription(), vehicle.getDescription());
        assertEquals(request.getPrice(), vehicle.getPrice());
        assertEquals(request.getVehicleCategoryId(), vehicle.getVehicleCategoryId());
        assertEquals(request.getModelId(), vehicle.getModelId());
        assertEquals(request.getVehicleStatus(), vehicle.getVehicleStatus());
    }

    @Disabled
    public void testFromEntity() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setCode("V123");
        vehicle.setCor("Red");
        vehicle.setVehicleYear(2021);
        vehicle.setDescription("Description");
        vehicle.setPrice(new BigDecimal("10000"));
        vehicle.setVehicleCategoryId(1L);
        vehicle.setModelId(2L);
        vehicle.setVehicleStatus("ACTIVE");

        // Act
        VehicleResponse response = mapper.fromEntity(vehicle);

        // Assert
        assertNotNull(response);
        assertEquals(vehicle.getId(), response.getId());
        assertEquals(vehicle.getCode(), response.getCode());
        assertEquals(vehicle.getCor(), response.getCor());
        assertEquals(vehicle.getVehicleYear(), response.getVehicleYear());
        assertEquals(vehicle.getDescription(), response.getDescription());
        assertEquals(vehicle.getPrice(), response.getPrice());
        assertEquals(vehicle.getVehicleStatus(), response.getVehicleStatus());
    }

    @Disabled
    public void testMapEntityListToModelList() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setCode("V123");
        vehicle1.setCor("Red");
        vehicle1.setVehicleYear(2021);
        vehicle1.setDescription("Description1");
        vehicle1.setPrice(new BigDecimal("10000"));
        vehicle1.setVehicleCategoryId(1L);
        vehicle1.setModelId(2L);
        vehicle1.setVehicleStatus("ACTIVE");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setCode("V124");
        vehicle2.setCor("Blue");
        vehicle2.setVehicleYear(2022);
        vehicle2.setDescription("Description2");
        vehicle2.setPrice(new BigDecimal("20000"));
        vehicle2.setVehicleCategoryId(3L);
        vehicle2.setModelId(4L);
        //vehicle2.setVehicleStatus("INACTIVE");

        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);

        // Act
        List<VehicleResponse> responseList = mapper.map(vehicleList);

        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(vehicle1.getId(), responseList.get(0).getId());
        assertEquals(vehicle1.getCode(), responseList.get(0).getCode());
        assertEquals(vehicle1.getCor(), responseList.get(0).getCor());
        assertEquals(vehicle1.getVehicleYear(), responseList.get(0).getVehicleYear());
        assertEquals(vehicle1.getDescription(), responseList.get(0).getDescription());
        assertEquals(vehicle1.getPrice(), responseList.get(0).getPrice());

        assertEquals(vehicle2.getId(), responseList.get(1).getId());
        assertEquals(vehicle2.getCode(), responseList.get(1).getCode());
        assertEquals(vehicle2.getCor(), responseList.get(1).getCor());
        assertEquals(vehicle2.getVehicleYear(), responseList.get(1).getVehicleYear());
        assertEquals(vehicle2.getDescription(), responseList.get(1).getDescription());
        assertEquals(vehicle2.getPrice(), responseList.get(1).getPrice());
    }
}