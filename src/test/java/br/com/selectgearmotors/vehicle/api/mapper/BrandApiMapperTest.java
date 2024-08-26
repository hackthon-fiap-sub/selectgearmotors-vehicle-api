package br.com.selectgearmotors.vehicle.api.mapper;

import br.com.selectgearmotors.vehicle.application.api.dto.request.BrandRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.BrandResponse;
import br.com.selectgearmotors.vehicle.application.api.mapper.BrandApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BrandApiMapperTest {

    private BrandApiMapper mapper = Mappers.getMapper(BrandApiMapper.class);

    @Test
    public void testFromRequest() {
        // Arrange
        BrandRequest request = new BrandRequest();
        request.setName("Test Brand");

        // Act
        Brand brand = mapper.fromRequest(request);

        // Assert
        assertNotNull(brand);
        assertEquals(request.getName(), brand.getName());
    }

    @Test
    public void testFromEntity() {
        // Arrange
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Test Brand");

        // Act
        BrandResponse response = mapper.fromEntity(brand);

        // Assert
        assertNotNull(response);
        assertEquals(brand.getId(), response.getId());
        assertEquals(brand.getName(), response.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Test Brand 1");

        Brand brand2 = new Brand();
        brand2.setId(2L);
        brand2.setName("Test Brand 2");

        List<Brand> brandList = new ArrayList<>();
        brandList.add(brand1);
        brandList.add(brand2);

        // Act
        List<BrandResponse> responseList = mapper.map(brandList);

        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(brand1.getId(), responseList.get(0).getId());
        assertEquals(brand1.getName(), responseList.get(0).getName());
        assertEquals(brand2.getId(), responseList.get(1).getId());
        assertEquals(brand2.getName(), responseList.get(1).getName());
    }
}