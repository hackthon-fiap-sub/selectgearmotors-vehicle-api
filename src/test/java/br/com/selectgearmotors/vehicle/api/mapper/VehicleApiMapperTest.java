package br.com.selectgearmotors.vehicle.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleResponse;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
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
        request.setPic("pic.jpg");
        request.setYear(2021);
        request.setDescription("Description");
        request.setPrice(new BigDecimal("10000"));
        request.setVehicleTypeId(1L);
        request.setModelId(3L);
        request.setVehicleStatus("ACTIVE");

        // Act
        Vehicle vehicle = mapper.fromRequest(request);

        // Assert
        assertNotNull(vehicle);
        assertEquals(request.getCor(), vehicle.getCor());
        assertEquals(request.getPic(), vehicle.getPic());
        assertEquals(request.getYear(), vehicle.getYear());
        assertEquals(request.getDescription(), vehicle.getDescription());
        assertEquals(request.getPrice(), vehicle.getPrice());
        assertEquals(request.getVehicleTypeId(), vehicle.getVehicleTypeId());
        assertEquals(request.getModelId(), vehicle.getModelId());
        assertEquals(request.getVehicleStatus(), vehicle.getVehicleStatus());
    }

    @Test
    public void testFromEntity() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setCode("V123");
        vehicle.setCor("Red");
        vehicle.setPic("pic.jpg");
        vehicle.setYear(2021);
        vehicle.setDescription("Description");
        vehicle.setPrice(new BigDecimal("10000"));
        vehicle.setVehicleTypeId(1L);
        vehicle.setBrandId(2L);
        vehicle.setModelId(3L);
        vehicle.setVehicleStatus("ACTIVE");

        // Act
        VehicleResponse response = mapper.fromEntity(vehicle);

        // Assert
        assertNotNull(response);
        assertEquals(vehicle.getId(), response.getId());
        assertEquals(vehicle.getCode(), response.getCode());
        assertEquals(vehicle.getCor(), response.getCor());
        assertEquals(vehicle.getPic(), response.getPic());
        assertEquals(vehicle.getYear(), response.getYear());
        assertEquals(vehicle.getDescription(), response.getDescription());
        assertEquals(vehicle.getPrice(), response.getPrice());
        assertEquals(vehicle.getVehicleTypeId(), response.getVehicleTypeEntity().getId());
        assertEquals(vehicle.getModelId(), response.getModelEntity().getId());
        assertEquals(vehicle.getVehicleStatus(), response.getVehicleStatus());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setCode("V123");
        vehicle1.setCor("Red");
        vehicle1.setPic("pic1.jpg");
        vehicle1.setYear(2021);
        vehicle1.setDescription("Description1");
        vehicle1.setPrice(new BigDecimal("10000"));
        vehicle1.setVehicleTypeId(1L);
        vehicle1.setBrandId(2L);
        vehicle1.setModelId(3L);
        vehicle1.setVehicleStatus("ACTIVE");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setCode("V124");
        vehicle2.setCor("Blue");
        vehicle2.setPic("pic2.jpg");
        vehicle2.setYear(2022);
        vehicle2.setDescription("Description2");
        vehicle2.setPrice(new BigDecimal("20000"));
        vehicle2.setVehicleTypeId(4L);
        vehicle2.setBrandId(5L);
        vehicle2.setModelId(6L);
        vehicle2.setVehicleStatus("INACTIVE");

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
        assertEquals(vehicle1.getPic(), responseList.get(0).getPic());
        assertEquals(vehicle1.getYear(), responseList.get(0).getYear());
        assertEquals(vehicle1.getDescription(), responseList.get(0).getDescription());
        assertEquals(vehicle1.getPrice(), responseList.get(0).getPrice());
        assertEquals(vehicle1.getVehicleTypeId(), responseList.get(0).getVehicleTypeEntity().getId());
        assertEquals(vehicle1.getModelId(), responseList.get(0).getModelEntity().getId());
        assertEquals(vehicle1.getVehicleStatus(), responseList.get(0).getVehicleStatus());

        assertEquals(vehicle2.getId(), responseList.get(1).getId());
        assertEquals(vehicle2.getCode(), responseList.get(1).getCode());
        assertEquals(vehicle2.getCor(), responseList.get(1).getCor());
        assertEquals(vehicle2.getPic(), responseList.get(1).getPic());
        assertEquals(vehicle2.getYear(), responseList.get(1).getYear());
        assertEquals(vehicle2.getDescription(), responseList.get(1).getDescription());
        assertEquals(vehicle2.getPrice(), responseList.get(1).getPrice());
        assertEquals(vehicle2.getVehicleTypeId(), responseList.get(1).getVehicleTypeEntity().getId());
        assertEquals(vehicle2.getModelId(), responseList.get(1).getModelEntity().getId());
        assertEquals(vehicle2.getVehicleStatus(), responseList.get(1).getVehicleStatus());
    }
}