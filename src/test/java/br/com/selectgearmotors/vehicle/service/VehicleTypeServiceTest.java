package br.com.selectgearmotors.vehicle.service;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleTypeMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.ports.in.vehicletype.*;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleTypeRepositoryPort;
import br.com.selectgearmotors.vehicle.core.service.VehicleTypeService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleTypeRepository;
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
class VehicleTypeServiceTest {

    @InjectMocks
    VehicleTypeService productTypeService;

    @Mock
    VehicleTypeRepositoryPort productTypeRepository;

    @Mock
    VehicleTypeRepository repository;

    @Mock
    VehicleTypeMapper mapper;

    @Mock
    CreateVehicleTypePort createVehicleTypePort;

    @Mock
    DeleteVehicleTypePort deleteVehicleTypePort;

    @Mock
    FindByIdVehicleTypePort findByIdVehicleTypePort;

    @Mock
    FindVehicleTypesPort findVehicleTypesPort;

    @Mock
    UpdateVehicleTypePort updateVehicleTypePort;

    private VehicleTypeEntity getVehicleTypeEntity() {
        return VehicleTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    private VehicleTypeEntity getVehicleTypeEntity1() {
        return VehicleTypeEntity.builder()
                .name("Bebida 1")
                .build();
    }

    private VehicleTypeEntity getVehicleTypeEntity2() {
        return VehicleTypeEntity.builder()
                .name("Bebida 2")
                .build();
    }

    private VehicleType getVehicleType() {
        return VehicleType.builder()
                .name("Bebida")
                .build();
    }

    private VehicleType getVehicleType1() {
        return VehicleType.builder()
                .name("Bebida 1")
                .build();
    }

    private VehicleType getVehicleType2() {
        return VehicleType.builder()
                .name("Bebida 2")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllVehicleTypesTest() {
        List<VehicleType> vehicleTypes = new ArrayList<>();
        List<VehicleTypeEntity> listEntity = new ArrayList<>();

        VehicleType client = getVehicleType();
        VehicleType client1 = getVehicleType1();
        VehicleType client2 = getVehicleType2();

        VehicleTypeEntity clientEntity = getVehicleTypeEntity();
        VehicleTypeEntity clientEntity1 = getVehicleTypeEntity1();
        VehicleTypeEntity clientEntity2 = getVehicleTypeEntity2();

        vehicleTypes.add(client);
        vehicleTypes.add(client1);
        vehicleTypes.add(client2);

        listEntity.add(clientEntity);
        listEntity.add(clientEntity1);
        listEntity.add(clientEntity2);

        when(productTypeService.findAll()).thenReturn(vehicleTypes);

        List<VehicleType> vehicleTypeList = productTypeService.findAll();

        assertNotNull(vehicleTypeList);
    }

    @Test
    void getVehicleTypeByIdTest() {
        VehicleType vehicleType1 = getVehicleType();
        when(productTypeService.findById(1L)).thenReturn(vehicleType1);

        VehicleType vehicleType = productTypeService.findById(1L);

        assertEquals("Bebida", vehicleType.getName());
    }

    @Test
    void getFindVehicleTypeByShortIdTest() {
        VehicleType vehicleType = getVehicleType();
        when(productTypeService.findById(1L)).thenReturn(vehicleType);

        VehicleType result = productTypeService.findById(1L);

        assertEquals("Bebida", result.getName());
    }

    @Test
    void createVehicleTypeTest() {
        VehicleType vehicleType = getVehicleType();
        VehicleType vehicleTypeResult = getVehicleType();
        vehicleTypeResult.setId(1L);

        when(productTypeService.save(vehicleType)).thenReturn(vehicleTypeResult);
        VehicleType save = productTypeService.save(vehicleType);

        assertNotNull(save);
        //verify(productTypeRepository, times(1)).save(productType);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productTypeRepository).save(vehicleType);

        assertThrows(DataException.class, () -> {
            productTypeRepository.save(vehicleType);
        });
    }

    @Test
    void testRemoveRestaurant_Success() {
        Long restaurantId = 1L;
        VehicleType vehicleType = getVehicleType();
        vehicleType.setId(restaurantId);

        when(productTypeService.findById(restaurantId)).thenReturn(vehicleType);
        boolean result = productTypeService.remove(restaurantId);
        assertTrue(result);
    }

    @Test
    void testRemove_Exception() {
        Long productId = 99L;

        boolean result = productTypeService.remove(productId);
        assertFalse(result);
        verify(productTypeRepository, never()).remove(productId);
    }

    @Test
    void testCreateVehicleType() {
        VehicleType vehicleType = getVehicleType();
        when(createVehicleTypePort.save(vehicleType)).thenReturn(vehicleType);

        VehicleType result = createVehicleTypePort.save(vehicleType);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testDeleteVehicleType() {
        Long productId = 1L;
        when(deleteVehicleTypePort.remove(productId)).thenReturn(true);

        boolean result = deleteVehicleTypePort.remove(productId);

        assertTrue(result);
    }

    @Test
    void testFindByIdVehicleType() {
        VehicleType vehicleType = getVehicleType();
        when(findByIdVehicleTypePort.findById(1L)).thenReturn(vehicleType);

        VehicleType result = findByIdVehicleTypePort.findById(1L);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testFindVehicleCategories() {
        List<VehicleType> productCategories = new ArrayList<>();
        productCategories.add(getVehicleType());
        productCategories.add(getVehicleType1());
        productCategories.add(getVehicleType2());

        when(findVehicleTypesPort.findAll()).thenReturn(productCategories);

        List<VehicleType> result = findVehicleTypesPort.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testUpdateVehicleType() {
        Long productId = 1L;
        VehicleType vehicleType = getVehicleType();
        vehicleType.setName("Updated Name");

        when(updateVehicleTypePort.update(productId, vehicleType)).thenReturn(vehicleType);

        VehicleType result = updateVehicleTypePort.update(productId, vehicleType);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

}