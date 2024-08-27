package br.com.selectgearmotors.vehicle.entity;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelEntityTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        ModelEntity entity = new ModelEntity(1L, "V$", new BrandEntity(1L, "Brand"));

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V$", entity.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        ModelEntity entity = new ModelEntity();

        // Act
        entity.setId(1L);
        entity.setName("V$");

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V$", entity.getName());
    }

    @Test
    public void testToString() {
        // Arrange
        ModelEntity entity = new ModelEntity(1L, "V$",  new BrandEntity(1L, "Brand"));

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ModelEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=V$"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        ModelEntity entity1 = new ModelEntity(1L, "V$",  new BrandEntity(1L, "Brand"));
        ModelEntity entity2 = new ModelEntity(1L, "V$",  new BrandEntity(1L, "Brand"));

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        ModelEntity entity1 = new ModelEntity(1L, "V$",  new BrandEntity(1L, "Brand"));
        ModelEntity entity2 = new ModelEntity(1L, "V$",  new BrandEntity(1L, "Brand"));
        ModelEntity entity3 = new ModelEntity(2L, "X$",  new BrandEntity(1L, "Brand"));

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}