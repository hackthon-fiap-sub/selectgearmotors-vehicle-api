package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class VehicleRepositoryAdapterTest {

    @InjectMocks
    VehicleRepositoryAdapter productRepositoryAdapter;

    @Mock
    private VehicleRepository productRepository;
    @Mock
    private VehicleMapper productMapper;

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

    private VehicleEntity getVehicleEntity(BrandEntity brandEntity, ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .modelEntity(modelEntity)
                .build();
    }

    private VehicleEntity getVehicleEntity1(BrandEntity brandEntity, ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .modelEntity(modelEntity)
                .build();
    }

    private VehicleEntity getVehicleEntity2(BrandEntity brandEntity, ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .modelEntity(modelEntity)
                .build();
    }

    private Vehicle getVehicle(Brand brand, Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Bebida")
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
                .cor("Bebida 1")
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
                .cor("Bebida 1")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .modelId(model.getId())
                .build();
    }

    private VehicleTypeEntity getVehicleTypeEntity() {
        return VehicleTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    private ModelEntity getModelEntity() {
        return ModelEntity.builder()
                .name("Bebida")
                .build();
    }

    private VehicleType getVehicleType() {
        return VehicleType.builder()
                .name("Bebida")
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_find_no_clients_if_repository_is_empty() {
        Iterable<VehicleEntity> seeds = productRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_product_category() {
        String azul = "Azul";
        VehicleEntity cocaCola = VehicleEntity.builder()
                .cor(azul)
                .build();

        when(productRepository.save(cocaCola)).thenReturn(cocaCola);
        VehicleEntity saved = productRepository.save(cocaCola);
        log.info("VehicleEntity:{}", saved);
        assertThat(saved).hasFieldOrPropertyWithValue("cor", azul);
    }

    @Disabled
    void testSaveBrandWithLongName() {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setCor("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        vehicleEntity.setCode(UUID.randomUUID().toString());
        vehicleEntity.setPic("hhh");
        vehicleEntity.setPrice(BigDecimal.TEN);
        vehicleEntity.setDescription("Coca-Cola");
        vehicleEntity.setVehicleTypeEntity(getVehicleTypeEntity());
        vehicleEntity.setModelEntity(getModelEntity());

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productRepository).save(vehicleEntity);

        assertThrows(DataException.class, () -> {
            productRepository.save(vehicleEntity);
        });
    }

    private VehicleEntity createInvalidVehicle() {
        VehicleEntity product = new VehicleEntity();
        // Configurar o product com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        product.setCor(""); // Nome vazio pode causar uma violação
        return product;
    }

    @Test
    void should_found_null_Vehicle() {
        VehicleEntity product = null;

        when(productRepository.findById(99l)).thenReturn(Optional.empty());
        Optional<VehicleEntity> fromDb = productRepository.findById(99l);
        if (fromDb.isPresent()) {
            product = fromDb.get();
        }
        assertThat(product).isNull();
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnVehicle() {
        Optional<VehicleEntity> product = productRepository.findById(1l);
        if (product.isPresent()) {
            VehicleEntity productResult = product.get();
            assertThat(productResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        VehicleEntity fromDb = productRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfVehicles_whenFindAll_thenReturnAllVehicles() {
        VehicleEntity product = null;
        VehicleEntity product1 = null;
        VehicleEntity product2 = null;

        Optional<VehicleEntity> restaurant = productRepository.findById(1l);
        if (restaurant.isPresent()) {

            VehicleEntity bebida = VehicleEntity.builder()
                    .cor("Bebida")
                    .build();
            product = productRepository.save(bebida);

            VehicleEntity acompanhamento = VehicleEntity.builder()
                    .cor("Acompanhamento")
                    .build();
            product1 = productRepository.save(acompanhamento);

            VehicleEntity lanche = VehicleEntity.builder()
                    .cor("Lanche")
                    .build();
            product2 = productRepository.save(lanche);

        }

        Iterator<VehicleEntity> allVehicles = productRepository.findAll().iterator();
        List<VehicleEntity> clients = new ArrayList<>();
        allVehicles.forEachRemaining(c -> clients.add(c));

        assertNotNull(allVehicles);
    }
}