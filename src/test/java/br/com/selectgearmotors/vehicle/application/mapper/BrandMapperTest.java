package br.com.selectgearmotors.vehicle.application.mapper;

import br.com.selectgearmotors.vehicle.application.database.mapper.BrandMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BrandMapperTest {

    private BrandMapper mapper = Mappers.getMapper(BrandMapper.class);

    @Test
    public void testFromModelToEntity() {
        // Arrange
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Test Brand");

        // Act
        BrandEntity entity = mapper.fromModelToEntity(brand);

        // Assert
        assertNotNull(entity);
        assertEquals(brand.getId(), entity.getId());
        assertEquals(brand.getName(), entity.getName());
    }

    @Test
    public void testFromEntityToModel() {
        // Arrange
        BrandEntity entity = new BrandEntity();
        entity.setId(1L);
        entity.setName("Test Brand");

        // Act
        Brand brand = mapper.fromEntityToModel(entity);

        // Assert
        assertNotNull(brand);
        assertEquals(entity.getId(), brand.getId());
        assertEquals(entity.getName(), brand.getName());
    }

    @Test
    public void testMapEntityListToModelList() {
        // Arrange
        BrandEntity entity1 = new BrandEntity();
        entity1.setId(1L);
        entity1.setName("Test Brand 1");

        BrandEntity entity2 = new BrandEntity();
        entity2.setId(2L);
        entity2.setName("Test Brand 2");

        List<BrandEntity> entityList = new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);

        // Act
        List<Brand> brandList = mapper.map(entityList);

        // Assert
        assertNotNull(brandList);
        assertEquals(2, brandList.size());
        assertEquals(entity1.getId(), brandList.get(0).getId());
        assertEquals(entity1.getName(), brandList.get(0).getName());
        assertEquals(entity2.getId(), brandList.get(1).getId());
        assertEquals(entity2.getName(), brandList.get(1).getName());
    }
}