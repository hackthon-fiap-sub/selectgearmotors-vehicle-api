package br.com.selectgearmotors.vehicle.service;

import br.com.selectgearmotors.vehicle.application.database.mapper.ModelMapper;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.ports.in.model.*;
import br.com.selectgearmotors.vehicle.core.ports.out.ModelRepositoryPort;
import br.com.selectgearmotors.vehicle.core.service.ModelService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

    @InjectMocks
    ModelService restaurantService;

    @Mock
    ModelRepositoryPort restaurantRepository;

    @Mock
    ModelRepository repository;

    @Mock
    ModelMapper mapper;

    @Mock
    CreateModelPort createModelPort;

    @Mock
    DeleteModelPort deleteModelPort;

    @Mock
    FindByIdModelPort findByIdModelPort;

    @Mock
    FindModelsPort findModelsPort;

    @Mock
    UpdateModelPort updateModelPort;

    private ModelEntity getModelEntity() {
        return ModelEntity.builder()
                .name("Seven Food")
                .build();
    }

    private ModelEntity getModelEntity1() {
        return ModelEntity.builder()
                .name("Seven Food Filial 1")
                .build();
    }

    private ModelEntity getModelEntity2() {
        return ModelEntity.builder()
                .name("Seven Food Filial 2")
                .build();
    }

    private Model getModel() {
        return Model.builder()
                .name("Seven Food")
                .build();
    }

    private Model getModel1() {
        return Model.builder()
                .name("Seven Food Filial 1")
                .build();
    }

    private Model getModel2() {
        return Model.builder()
                .name("Seven Food Filial 2")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllModelsTest() {
        List<Model> restaurants = new ArrayList<>();
        List<ModelEntity> listEntity = new ArrayList<>();

        Model client = getModel();
        Model client1 = getModel1();
        Model client2 = getModel2();

        ModelEntity clientEntity = getModelEntity();
        ModelEntity clientEntity1 = getModelEntity1();
        ModelEntity clientEntity2 = getModelEntity2();

        restaurants.add(client);
        restaurants.add(client1);
        restaurants.add(client2);

        listEntity.add(clientEntity);
        listEntity.add(clientEntity1);
        listEntity.add(clientEntity2);

        when(restaurantService.findAll()).thenReturn(restaurants);

        List<Model> restaurantList = restaurantService.findAll();

        assertNotNull(restaurantList);
    }

    @Test
    void getModelByIdTest() {
        Model restaurant1 = getModel();
        when(restaurantService.findById(1L)).thenReturn(restaurant1);

        Model restaurant = restaurantService.findById(1L);

        assertEquals("Seven Food", restaurant.getName());
    }

    @Test
    void getFindModelByShortIdTest() {
        Model restaurant = getModel();
        when(restaurantService.findById(1L)).thenReturn(restaurant);

        Model result = restaurantService.findById(1L);

        assertEquals("Seven Food", result.getName());
    }

    @Test
    void createModelTest() {
        Model restaurant = getModel();
        Model restaurantResult = getModel();
        restaurantResult.setId(1L);

        when(restaurantService.save(restaurant)).thenReturn(restaurantResult);
        Model save = restaurantService.save(restaurant);

        assertNotNull(save);
        //verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testSaveModelWithLongName() {
        Model restaurant = new Model();
        restaurant.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(restaurantRepository).save(restaurant);

        assertThrows(DataException.class, () -> {
            restaurantRepository.save(restaurant);
        });
    }

    @Test
    void testRemoveModel_Success() {
        Long restaurantId = 1L;
        Model restaurant = getModel(); // Método que cria um objeto Model para o teste
        restaurant.setId(restaurantId);

        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);
        boolean result = restaurantService.remove(restaurantId);
        assertTrue(result);
    }

    @Test
    void testRemove_Exception() {
        Long productId = 99L;

        boolean result = restaurantService.remove(productId);
        assertFalse(result);
        verify(restaurantRepository, never()).remove(productId);
    }

    @Test
    void createModelPortTest() {
        Model restaurant = getModel();
        Model restaurantResult = getModel();
        restaurantResult.setId(1L);

        CreateModelPort createModelPort = mock(CreateModelPort.class);
        when(createModelPort.save(restaurant)).thenReturn(restaurantResult);

        Model savedModel = createModelPort.save(restaurant);

        assertNotNull(savedModel);
        assertEquals(1L, savedModel.getId());
        assertEquals("Seven Food", savedModel.getName());
    }

    @Test
    void deleteModelPortTest() {
        Long restaurantId = 1L;

        DeleteModelPort deleteModelPort = mock(DeleteModelPort.class);
        when(deleteModelPort.remove(restaurantId)).thenReturn(true);

        boolean result = deleteModelPort.remove(restaurantId);

        assertTrue(result);
    }

    @Test
    void findByIdModelPortTest() {
        Long restaurantId = 1L;
        Model restaurant = getModel();
        restaurant.setId(restaurantId);

        FindByIdModelPort findByIdModelPort = mock(FindByIdModelPort.class);
        when(findByIdModelPort.findById(restaurantId)).thenReturn(restaurant);

        Model foundModel = findByIdModelPort.findById(restaurantId);

        assertNotNull(foundModel);
        assertEquals("Seven Food", foundModel.getName());
    }

    @Test
    void findModelsPortTest() {
        List<Model> restaurants = new ArrayList<>();
        restaurants.add(getModel());
        restaurants.add(getModel1());
        restaurants.add(getModel2());

        FindModelsPort findModelsPort = mock(FindModelsPort.class);
        when(findModelsPort.findAll()).thenReturn(restaurants);

        List<Model> restaurantList = findModelsPort.findAll();

        assertNotNull(restaurantList);
        assertEquals(3, restaurantList.size());
    }

    @Test
    void updateModelPortTest() {
        Long restaurantId = 1L;
        Model restaurant = getModel();
        restaurant.setId(restaurantId);
        restaurant.setName("Updated Name");

        UpdateModelPort updateModelPort = mock(UpdateModelPort.class);
        when(updateModelPort.update(restaurantId, restaurant)).thenReturn(restaurant);

        Model updatedModel = updateModelPort.update(restaurantId, restaurant);

        assertNotNull(updatedModel);
        assertEquals("Updated Name", updatedModel.getName());
    }
}