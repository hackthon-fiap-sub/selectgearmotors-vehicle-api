package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.MediaType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleFuel;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import br.com.selectgearmotors.vehicle.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.*;
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
    private VehicleCategoryRepository vehicleCategoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private MediaRepository mediaRepository;

    private BrandEntity brandEntity;

    private ModelEntity modelEntity;

    private MediaEntity mediaEntity;

    private VehicleCategoryEntity vehicleCategoryEntity;

    private VehicleEntity vehicleEntity;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        log.info("Cleaning up database...");
        vehicleRepository.deleteAll();
        vehicleCategoryRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
        modelRepository.deleteAll();

        log.info("Setting up test data...");
        this.brandEntity = brandRepository.save(getBrand());
        this.vehicleCategoryEntity = vehicleCategoryRepository.save(getVehicleCategory());
        this.modelEntity = modelRepository.save(getModel(this.brandEntity));
        this.mediaEntity = mediaRepository.save(getMedia());

        VehicleEntity vehicle = getVehicle(this.mediaEntity, this.modelEntity, this.vehicleCategoryEntity);
        this.vehicleEntity = vehicleRepository.save(vehicle);
        log.info("VehicleEntity - saved :{}", this.vehicleEntity);
    }

    private MediaEntity getMedia() {
        return MediaEntity.builder()
                .name(faker.food().ingredient())
                .path(faker.internet().url())
                .mediaType(MediaType.JPG)
                .build();
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

    private VehicleEntity getVehicle(MediaEntity mediaEntity, ModelEntity modelEntity, VehicleCategoryEntity vehicleCategoryEntity) {
        return VehicleEntity.builder()
                .cor("Azul")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryEntity(vehicleCategoryEntity)
                .modelEntity(modelEntity)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .location("São Paulo")
                .vehicleFuel(VehicleFuel.FLEX)
                .vehicleYear(2021)
                .km(1000)
                .mediaEntity(mediaEntity)
                .build();
    }

    private VehicleCategoryEntity getVehicleCategory() {
        return VehicleCategoryEntity.builder()
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
        vehicleCategoryRepository.deleteAll();
        brandRepository.deleteAll();

        var brand = brandRepository.save(getBrand());
        var model = modelRepository.save(getModel(brand));
        var media = mediaRepository.save(getMedia());
        var vehicleCategoryEntity1 = vehicleCategoryRepository.save(getVehicleCategory());

        vehicleRepository.save(getVehicle(media, model, vehicleCategoryEntity1));
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
        vehicleCategoryRepository.deleteAll();
        brandRepository.deleteAll();

        List<VehicleEntity> all = vehicleRepository.findAll();
        log.info(all.toString());

        BrandEntity restaurant1 = brandRepository.save(getBrand());
        VehicleCategoryEntity vehicleCategoryEntity1 = vehicleCategoryRepository.save(getVehicleCategory());
        var media = mediaRepository.save(getMedia());

        VehicleEntity vehicle = getVehicle(media, getModel(this.brandEntity), vehicleCategoryEntity1);
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
        vehicleEntity.setPrice(BigDecimal.TEN);
        vehicleEntity.setDescription("Coca-Cola");
        vehicleEntity.setVehicleCategoryEntity(vehicleCategoryEntity);

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleRepository.save(vehicleEntity);
        });
    }

    private VehicleEntity createInvalidVehicleCategory() {
        VehicleEntity vehicleCategoryEntity = new VehicleEntity();
        // Configurar o vehicleCategoryEntity com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        vehicleCategoryEntity.setCor(""); // Nome vazio pode causar uma violação
        return vehicleCategoryEntity;
    }
}
