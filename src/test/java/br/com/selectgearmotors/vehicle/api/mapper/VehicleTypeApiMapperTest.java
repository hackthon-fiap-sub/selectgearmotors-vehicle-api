package br.com.selectgearmotors.vehicle.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleTypeRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleTypeResponse;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleTypeApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeApiMapperTest {

    private VehicleTypeApiMapper mapper = Mappers.getMapper(VehicleTypeApiMapper.class);

    @Test
    public void testFromRequest() {
        // Arrange
        VehicleTypeRequest request = new VehicleTypeRequest();
        request.setName("SUV");

        // Act
        VehicleType vehicleType = mapper.fromRequest(request);

        // Assert
        assertNotNull(vehicleType);
        assertEquals(request.getName(), vehicleType.getName());
    }

    @Test
    public void testFromEntity() {
        // Arrange
        VehicleType vehicleType = new VehicleType();
        vehicleType.setId(1L);
        vehicleType.setName("SUV");

        // Act
        VehicleTypeResponse response = mapper.fromEntity(vehicleType);

        // Assert
        assertNotNull(response);
        assertEquals(vehicleType.getId(), response.getId());
        assertEquals(vehicleType.getName(), response.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        VehicleType vehicleType1 = new VehicleType();
        vehicleType1.setId(1L);
        vehicleType1.setName("SUV");

        VehicleType vehicleType2 = new VehicleType();
        vehicleType2.setId(2L);
        vehicleType2.setName("Sedan");

        List<VehicleType> vehicleTypeList = new ArrayList<>();
        vehicleTypeList.add(vehicleType1);
        vehicleTypeList.add(vehicleType2);

        // Act
        List<VehicleTypeResponse> responseList = mapper.map(vehicleTypeList);

        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(vehicleType1.getId(), responseList.get(0).getId());
        assertEquals(vehicleType1.getName(), responseList.get(0).getName());
        assertEquals(vehicleType2.getId(), responseList.get(1).getId());
        assertEquals(vehicleType2.getName(), responseList.get(1).getName());
    }
}