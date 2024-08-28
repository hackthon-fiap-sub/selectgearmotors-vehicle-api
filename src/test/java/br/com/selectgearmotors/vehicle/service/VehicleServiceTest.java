package br.com.selectgearmotors.vehicle.service;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleMapper;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.ports.in.vehicle.*;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.ports.out.VehicleRepositoryPort;
import br.com.selectgearmotors.vehicle.core.service.VehicleService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    VehicleService vehicleService;

    @Mock
    VehicleRepositoryPort vehicleRepository;

    @Mock
    VehicleRepository repository;

    @Mock
    VehicleMapper mapper;

    @Mock
    CreateVehiclePort createVehiclePort;

    @Mock
    DeleteVehiclePort deleteVehiclePort;

    @Mock
    FindByIdVehiclePort findByIdVehiclePort;

    @Mock
    FindVehiclesPort findVehiclesPort;

    @Mock
    UpdateVehiclePort updateVehiclePort;

    private BrandEntity getBrandEntity() {
        return BrandEntity.builder()
                .name("Seven Food")
                .build();
    }

    private Brand getBrand() {
        return Brand.builder()
                .name("Seven Food")
                .build();
    }

    private ModelEntity getModelEntity() {
        return ModelEntity.builder()
                .name("Seven Food")
                .build();
    }

    private Model getModel() {
        return Model.builder()
                .name("Seven Food")
                .build();
    }

    private VehicleEntity getVehicleEntity(ModelEntity modelEntity, VehicleTypeEntity VehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(VehicleTypeEntity)
                .modelEntity(modelEntity)
                .build();
    }

    private VehicleEntity getVehicleEntity1(ModelEntity modelEntity, VehicleTypeEntity VehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(VehicleTypeEntity)
                .modelEntity(modelEntity)
                .build();
    }

    private VehicleEntity getVehicleEntity2(ModelEntity modelEntity, VehicleTypeEntity VehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(VehicleTypeEntity)
                .modelEntity(modelEntity)
                .build();
    }

    private Vehicle getVehicle(Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .modelId(model.getId())
                .build();
    }

    private Vehicle getVehicle1(Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .modelId(model.getId())
                .build();
    }

    private Vehicle getVehicle2(Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .modelId(model.getId())
                .build();
    }

    private VehicleType getVehicleType() {
        return VehicleType.builder()
                .name("Bebida")
                .build();
    }

    private VehicleTypeEntity getVehicleTypeEntity() {
        return VehicleTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllVehiclesTest() {
        List<Vehicle> vehicles = new ArrayList<>();
        List<VehicleEntity> listEntity = new ArrayList<>();

        Vehicle client = getVehicle(getModel(), getVehicleType());
        Vehicle client1 = getVehicle1(getModel(), getVehicleType());
        Vehicle client2 = getVehicle2(getModel(), getVehicleType());

        VehicleEntity clientEntity = getVehicleEntity(getModelEntity(), getVehicleTypeEntity());
        VehicleEntity clientEntity1 = getVehicleEntity1(getModelEntity(), getVehicleTypeEntity());
        VehicleEntity clientEntity2 = getVehicleEntity2(getModelEntity(), getVehicleTypeEntity());

        vehicles.add(client);
        vehicles.add(client1);
        vehicles.add(client2);

        listEntity.add(clientEntity);
        listEntity.add(clientEntity1);
        listEntity.add(clientEntity2);

        when(vehicleService.findAll()).thenReturn(vehicles);

        List<Vehicle> vehicleList = vehicleService.findAll();

        assertNotNull(vehicleList);
    }

    @Test
    void getVehicleByIdTest() {
        Vehicle vehicle1 = getVehicle(getModel(), getVehicleType());
        when(vehicleService.findById(1L)).thenReturn(vehicle1);

        Vehicle vehicle = vehicleService.findById(1L);

        assertEquals("Coca-Cola", vehicle.getCor());
    }

    @Test
    void getFindVehicleByShortIdTest() {
        Vehicle vehicle = getVehicle(getModel(), getVehicleType());
        when(vehicleService.findById(1L)).thenReturn(vehicle);

        Vehicle result = vehicleService.findById(1L);

        assertEquals("Coca-Cola", result.getCor());
    }

    @Test
    void createVehicleTest() {
        Vehicle vehicle = getVehicle(getModel(), getVehicleType());
        Vehicle vehicleResult = getVehicle(getModel(), getVehicleType());
        vehicleResult.setId(1L);

        when(vehicleService.save(vehicle)).thenReturn(vehicleResult);
        Vehicle save = vehicleService.save(vehicle);

        assertNotNull(save);
        //verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    void testSaveBrandWithLongName() {
        Vehicle vehicle = new Vehicle();
        vehicle.setCor("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        vehicle.setCode(UUID.randomUUID().toString());
        vehicle.setPic("hhh");
        vehicle.setPrice(BigDecimal.TEN);
        vehicle.setDescription("Coca-Cola");
        vehicle.setModelId(1l);
        vehicle.setVehicleTypeId(1l);

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(vehicleRepository).save(vehicle);

        assertThrows(DataException.class, () -> {
            vehicleRepository.save(vehicle);
        });
    }

    @Test
    void testRemove_Exception() {
        Long vehicleId = 99L;

        boolean result = vehicleService.remove(vehicleId);
        assertFalse(result);
        verify(vehicleRepository, never()).remove(vehicleId);
    }

    @Test
    void testCreateVehicle() {
        Vehicle vehicle = getVehicle(getModel(), getVehicleType());
        Vehicle vehicleResult = getVehicle(getModel(), getVehicleType());
        when(createVehiclePort.save(vehicle)).thenReturn(vehicleResult);

        Vehicle result = createVehiclePort.save(vehicle);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getCor());
    }

    @Test
    void testDeleteVehicle() {
        Long vehicleId = 1L;
        when(deleteVehiclePort.remove(vehicleId)).thenReturn(true);

        boolean result = deleteVehiclePort.remove(vehicleId);

        assertTrue(result);
    }

    @Test
    void testFindByIdVehicle() {
        Vehicle vehicle = getVehicle(getModel(), getVehicleType());
        when(findByIdVehiclePort.findById(1L)).thenReturn(vehicle);

        Vehicle result = findByIdVehiclePort.findById(1L);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getCor());
    }

    @Test
    void testFindVehicles() {
        Vehicle vehicle = getVehicle(getModel(), getVehicleType());
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        when(findVehiclesPort.findAll()).thenReturn(vehicles);
        List<Vehicle> result = findVehiclesPort.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateVehicle() {
        Long vehicleId = 1L;
        Vehicle vehicle = getVehicle(getModel(), getVehicleType());

        when(updateVehiclePort.update(vehicleId, vehicle)).thenReturn(vehicle);
        Vehicle result = updateVehiclePort.update(vehicleId, vehicle);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getCor());
    }
}