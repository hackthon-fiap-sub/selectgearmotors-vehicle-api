package br.com.selectgearmotors.vehicle.service;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleCategoryMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.ports.in.vehiclecategory.*;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleCategoryRepositoryPort;
import br.com.selectgearmotors.vehicle.core.service.VehicleCategoryService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleCategoryRepository;
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
class VehicleCategoryServiceTest {

    @InjectMocks
    VehicleCategoryService productCategoryService;

    @Mock
    VehicleCategoryRepositoryPort productCategoryRepository;

    @Mock
    VehicleCategoryRepository repository;

    @Mock
    VehicleCategoryMapper mapper;

    @Mock
    CreateVehicleCategoryPort createVehicleCategoryPort;

    @Mock
    DeleteVehicleCategoryPort deleteVehicleCategoryPort;

    @Mock
    FindByIdVehicleCategoryPort findByIdVehicleCategoryPort;

    @Mock
    FindVehicleCategoriesPort findVehicleCategorysPort;

    @Mock
    UpdateVehicleCategoryPort updateVehicleCategoryPort;

    private VehicleCategoryEntity getVehicleCategoryEntity() {
        return VehicleCategoryEntity.builder()
                .name("Bebida")
                .build();
    }

    private VehicleCategoryEntity getVehicleCategoryEntity1() {
        return VehicleCategoryEntity.builder()
                .name("Bebida 1")
                .build();
    }

    private VehicleCategoryEntity getVehicleCategoryEntity2() {
        return VehicleCategoryEntity.builder()
                .name("Bebida 2")
                .build();
    }

    private VehicleCategory getVehicleCategory() {
        return VehicleCategory.builder()
                .name("Bebida")
                .build();
    }

    private VehicleCategory getVehicleCategory1() {
        return VehicleCategory.builder()
                .name("Bebida 1")
                .build();
    }

    private VehicleCategory getVehicleCategory2() {
        return VehicleCategory.builder()
                .name("Bebida 2")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllVehicleCategorysTest() {
        List<VehicleCategory> vehicleCategories = new ArrayList<>();
        List<VehicleCategoryEntity> listEntity = new ArrayList<>();

        VehicleCategory client = getVehicleCategory();
        VehicleCategory client1 = getVehicleCategory1();
        VehicleCategory client2 = getVehicleCategory2();

        VehicleCategoryEntity clientEntity = getVehicleCategoryEntity();
        VehicleCategoryEntity clientEntity1 = getVehicleCategoryEntity1();
        VehicleCategoryEntity clientEntity2 = getVehicleCategoryEntity2();

        vehicleCategories.add(client);
        vehicleCategories.add(client1);
        vehicleCategories.add(client2);

        listEntity.add(clientEntity);
        listEntity.add(clientEntity1);
        listEntity.add(clientEntity2);

        when(productCategoryService.findAll()).thenReturn(vehicleCategories);

        List<VehicleCategory> vehicleCategoryList = productCategoryService.findAll();

        assertNotNull(vehicleCategoryList);
    }

    @Test
    void getVehicleCategoryByIdTest() {
        VehicleCategory vehicleCategory1 = getVehicleCategory();
        when(productCategoryService.findById(1L)).thenReturn(vehicleCategory1);

        VehicleCategory vehicleCategory = productCategoryService.findById(1L);

        assertEquals("Bebida", vehicleCategory.getName());
    }

    @Test
    void getFindVehicleCategoryByShortIdTest() {
        VehicleCategory vehicleCategory = getVehicleCategory();
        when(productCategoryService.findById(1L)).thenReturn(vehicleCategory);

        VehicleCategory result = productCategoryService.findById(1L);

        assertEquals("Bebida", result.getName());
    }

    @Test
    void createVehicleCategoryTest() {
        VehicleCategory vehicleCategory = getVehicleCategory();
        VehicleCategory vehicleCategoryResult = getVehicleCategory();
        vehicleCategoryResult.setId(1L);

        when(productCategoryService.save(vehicleCategory)).thenReturn(vehicleCategoryResult);
        VehicleCategory save = productCategoryService.save(vehicleCategory);

        assertNotNull(save);
        //verify(productCategoryRepository, times(1)).save(productCategory);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        VehicleCategory vehicleCategory = new VehicleCategory();
        vehicleCategory.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productCategoryRepository).save(vehicleCategory);

        assertThrows(DataException.class, () -> {
            productCategoryRepository.save(vehicleCategory);
        });
    }

    @Test
    void testRemoveRestaurant_Success() {
        Long restaurantId = 1L;
        VehicleCategory vehicleCategory = getVehicleCategory();
        vehicleCategory.setId(restaurantId);

        when(productCategoryService.findById(restaurantId)).thenReturn(vehicleCategory);
        boolean result = productCategoryService.remove(restaurantId);
        assertTrue(result);
    }

    @Test
    void testRemove_Exception() {
        Long productId = 99L;

        boolean result = productCategoryService.remove(productId);
        assertFalse(result);
        verify(productCategoryRepository, never()).remove(productId);
    }

    @Test
    void testCreateVehicleCategory() {
        VehicleCategory vehicleCategory = getVehicleCategory();
        when(createVehicleCategoryPort.save(vehicleCategory)).thenReturn(vehicleCategory);

        VehicleCategory result = createVehicleCategoryPort.save(vehicleCategory);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testDeleteVehicleCategory() {
        Long productId = 1L;
        when(deleteVehicleCategoryPort.remove(productId)).thenReturn(true);

        boolean result = deleteVehicleCategoryPort.remove(productId);

        assertTrue(result);
    }

    @Test
    void testFindByIdVehicleCategory() {
        VehicleCategory vehicleCategory = getVehicleCategory();
        when(findByIdVehicleCategoryPort.findById(1L)).thenReturn(vehicleCategory);

        VehicleCategory result = findByIdVehicleCategoryPort.findById(1L);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testFindVehicleCategories() {
        List<VehicleCategory> productCategories = new ArrayList<>();
        productCategories.add(getVehicleCategory());
        productCategories.add(getVehicleCategory1());
        productCategories.add(getVehicleCategory2());

        when(findVehicleCategorysPort.findAll()).thenReturn(productCategories);

        List<VehicleCategory> result = findVehicleCategorysPort.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testUpdateVehicleCategory() {
        Long productId = 1L;
        VehicleCategory vehicleCategory = getVehicleCategory();
        vehicleCategory.setName("Updated Name");

        when(updateVehicleCategoryPort.update(productId, vehicleCategory)).thenReturn(vehicleCategory);

        VehicleCategory result = updateVehicleCategoryPort.update(productId, vehicleCategory);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

}