package br.com.selectgearmotors.vehicle.domain;

import br.com.selectgearmotors.vehicle.core.domain.Brand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrandTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        Brand brand = new Brand(1L, "BrandName");

        // Assert
        assertEquals(1L, brand.getId());
        assertEquals("BrandName", brand.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Brand brand = new Brand();

        // Act
        brand.setId(1L);
        brand.setName("BrandName");

        // Assert
        assertEquals(1L, brand.getId());
        assertEquals("BrandName", brand.getName());
    }

    @Test
    public void testToString() {
        // Arrange
        Brand brand = new Brand(1L, "BrandName");

        // Act
        String toString = brand.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Brand"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=BrandName"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        Brand brand1 = new Brand(1L, "BrandName");
        Brand brand2 = new Brand(1L, "BrandName");

        // Act & Assert
        assertEquals(brand1.hashCode(), brand2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        Brand brand1 = new Brand(1L, "BrandName");
        Brand brand2 = new Brand(1L, "BrandName");
        Brand brand3 = new Brand(2L, "AnotherBrand");

        // Act & Assert
        assertEquals(brand1, brand2);
        assertNotEquals(brand1, brand3);
    }
}