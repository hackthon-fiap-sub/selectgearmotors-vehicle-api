package br.com.selectgearmotors.vehicle.domain;

import br.com.selectgearmotors.vehicle.core.domain.Model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        Model model = new Model(1L, "ModelName");

        // Assert
        assertEquals(1L, model.getId());
        assertEquals("ModelName", model.getName());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Model model = new Model();

        // Act
        model.setId(1L);
        model.setName("ModelName");

        // Assert
        assertEquals(1L, model.getId());
        assertEquals("ModelName", model.getName());
    }

    @Test
    public void testToString() {
        // Arrange
        Model model = new Model(1L, "ModelName");

        // Act
        String toString = model.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Model"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=ModelName"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        Model model1 = new Model(1L, "ModelName");
        Model model2 = new Model(1L, "ModelName");

        // Act & Assert
        assertEquals(model1.hashCode(), model2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        Model model1 = new Model(1L, "ModelName");
        Model model2 = new Model(1L, "ModelName");
        Model model3 = new Model(2L, "AnotherModel");

        // Act & Assert
        assertEquals(model1, model2);
        assertNotEquals(model1, model3);
    }
}