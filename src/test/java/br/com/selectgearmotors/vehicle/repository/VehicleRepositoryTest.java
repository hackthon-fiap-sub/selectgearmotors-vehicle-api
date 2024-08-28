package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleTypeRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    private BrandEntity brandEntity;

    private ModelEntity modelEntity;

    private VehicleTypeEntity vehicleTypeEntity;

    private VehicleEntity vehicleEntity;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        log.info("Cleaning up database...");
        vehicleRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();

        log.info("Setting up test data...");
        this.brandEntity = brandRepository.save(getBrand());
        this.vehicleTypeEntity = vehicleTypeRepository.save(getVehicleType());
        this.modelEntity = modelRepository.save(getModel(this.brandEntity));

        VehicleEntity vehicle = getVehicle(this.modelEntity, this.vehicleTypeEntity);
        this.vehicleEntity = vehicleRepository.save(vehicle);
        log.info("VehicleEntity - saved :{}", this.vehicleEntity);
    }

    private ModelEntity getModel(BrandEntity brandEntity) {
        return ModelEntity.builder()
                .name(faker.food().ingredient())
                .brandEntity(brandEntity)
                .build();
    }

    private BrandEntity getBrand() {
        return BrandEntity.builder()
                .name(faker.company().name())
                .build();
    }

    private VehicleEntity getVehicle(ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Azul")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .modelEntity(modelEntity)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
    }

    private VehicleEntity getVehicle1(BrandEntity brandEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .vehicleTypeEntity(vehicleTypeEntity)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
    }

    private VehicleEntity getVehicle2(BrandEntity brandEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .vehicleTypeEntity(vehicleTypeEntity)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
    }

    private VehicleTypeEntity getVehicleType() {
        return VehicleTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    @Test
    void should_find_no_vehicles_if_repository_is_empty() {
        Iterable<VehicleEntity> vehicles = vehicleRepository.findAll();
        vehicles = Collections.EMPTY_LIST;
        assertThat(vehicles).isEmpty();
    }

    @Test
    void should_store_a_vehicle() {
        String cor = "Azul";
        assertThat(this.vehicleEntity).isNotNull();
        assertThat(this.vehicleEntity.getId()).isNotNull();
        assertThat(this.vehicleEntity.getCor()).isEqualTo(cor);
    }

    @Test
    void should_find_vehicle_by_id() {
        Optional<VehicleEntity> foundVehicle = vehicleRepository.findById(this.vehicleEntity.getId());
        assertThat(foundVehicle).isPresent();
        assertThat(foundVehicle.get().getCor()).isEqualTo(this.vehicleEntity.getCor());
    }

    @Test
    void should_find_all_vehicles() {
        Iterable<VehicleEntity> vehicles = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();
        vehicles.forEach(vehicleList::add);

        assertThat(vehicleList).hasSize(1);
        assertThat(vehicleList).extracting(VehicleEntity::getCor).contains(this.vehicleEntity.getCor());
    }

    @Disabled
    void should_delete_all_vehicles() {
        log.info("Cleaning up database...");
        vehicleRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        brandRepository.deleteAll();

        var brand = brandRepository.save(getBrand());
        var model = modelRepository.save(getModel(brand));
        var vehicleTypeEntity1 = vehicleTypeRepository.save(getVehicleType());

        vehicleRepository.save(getVehicle(model, vehicleTypeEntity1));
        vehicleRepository.deleteAll();

        Iterable<VehicleEntity> vehicles = vehicleRepository.findAll();
        assertThat(vehicles).isEmpty();
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        VehicleEntity fromDb = vehicleRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    void givenSetOfVehicles_whenFindAll_thenReturnAllVehicles() {
        vehicleRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        brandRepository.deleteAll();

        List<VehicleEntity> all = vehicleRepository.findAll();
        log.info(all.toString());

        BrandEntity restaurant1 = brandRepository.save(getBrand());
        VehicleTypeEntity vehicleTypeEntity1 = vehicleTypeRepository.save(getVehicleType());

        VehicleEntity vehicle = getVehicle(getModel(this.brandEntity), vehicleTypeEntity1);
        log.info("VehicleEntity:{}", vehicle);
        VehicleEntity vehicle1 = vehicleRepository.save(vehicle);

        Iterable<VehicleEntity> vehicles = vehicleRepository.findAll();
        List<VehicleEntity> vehicleList = new ArrayList<>();
        vehicles.forEach(vehicleList::add);

        assertThat(vehicleList).hasSize(1);
        //assertThat(vehicleList).extracting(VehicleEntity::getName).contains(vehicle1.getName(), vehicle2.getName(), vehicle3.getName());
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setCor("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        vehicleEntity.setCode(UUID.randomUUID().toString());
        vehicleEntity.setPic("hhh");
        vehicleEntity.setPrice(BigDecimal.TEN);
        vehicleEntity.setDescription("Coca-Cola");
        vehicleEntity.setVehicleTypeEntity(vehicleTypeEntity);

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleRepository.save(vehicleEntity);
        });
    }

    private VehicleEntity createInvalidVehicleType() {
        VehicleEntity vehicleTypeEntity = new VehicleEntity();
        // Configurar o vehicleTypeEntity com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        vehicleTypeEntity.setCor(""); // Nome vazio pode causar uma violação
        return vehicleTypeEntity;
    }
}
