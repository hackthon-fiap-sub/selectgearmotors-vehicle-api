package br.com.selectgearmotors.vehicle.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleCategoryRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleCategoryResponse;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleCategoryApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleCategoryApiMapperTest {

    private VehicleCategoryApiMapper mapper = Mappers.getMapper(VehicleCategoryApiMapper.class);

    @Test
    public void testFromRequest() {
        // Arrange
        VehicleCategoryRequest request = new VehicleCategoryRequest();
        request.setName("SUV");

        // Act
        VehicleCategory vehicleCategory = mapper.fromRequest(request);

        // Assert
        assertNotNull(vehicleCategory);
        assertEquals(request.getName(), vehicleCategory.getName());
    }

    @Test
    public void testFromEntity() {
        // Arrange
        VehicleCategory vehicleCategory = new VehicleCategory();
        vehicleCategory.setId(1L);
        vehicleCategory.setName("SUV");

        // Act
        VehicleCategoryResponse response = mapper.fromEntity(vehicleCategory);

        // Assert
        assertNotNull(response);
        assertEquals(vehicleCategory.getId(), response.getId());
        assertEquals(vehicleCategory.getName(), response.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        VehicleCategory vehicleCategory1 = new VehicleCategory();
        vehicleCategory1.setId(1L);
        vehicleCategory1.setName("SUV");

        VehicleCategory vehicleCategory2 = new VehicleCategory();
        vehicleCategory2.setId(2L);
        vehicleCategory2.setName("Sedan");

        List<VehicleCategory> vehicleCategoryList = new ArrayList<>();
        vehicleCategoryList.add(vehicleCategory1);
        vehicleCategoryList.add(vehicleCategory2);

        // Act
        List<VehicleCategoryResponse> responseList = mapper.map(vehicleCategoryList);

        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(vehicleCategory1.getId(), responseList.get(0).getId());
        assertEquals(vehicleCategory1.getName(), responseList.get(0).getName());
        assertEquals(vehicleCategory2.getId(), responseList.get(1).getId());
        assertEquals(vehicleCategory2.getName(), responseList.get(1).getName());
    }
}