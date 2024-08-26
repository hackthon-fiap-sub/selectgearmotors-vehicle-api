package br.com.selectgearmotors.vehicle.entity;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrandEntityTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        BrandEntity entity = new BrandEntity(1L, "V$");

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("V$", entity.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        BrandEntity entity = new BrandEntity();

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
        BrandEntity entity = new BrandEntity(1L, "V$");

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("BrandEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=V$"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        BrandEntity entity1 = new BrandEntity(1L, "V$");
        BrandEntity entity2 = new BrandEntity(1L, "V$");

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        BrandEntity entity1 = new BrandEntity(1L, "V$");
        BrandEntity entity2 = new BrandEntity(1L, "V$");
        BrandEntity entity3 = new BrandEntity(2L, "X$");

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}