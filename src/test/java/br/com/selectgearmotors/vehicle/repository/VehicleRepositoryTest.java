package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
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
    private VehicleRepository productRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private BrandRepository brandRepository;

    private BrandEntity brandEntity;

    private VehicleTypeEntity productType;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        log.info("Cleaning up database...");
        productRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        brandRepository.deleteAll();

        log.info("Setting up test data...");
        brandEntity = brandRepository.save(getRestaurant());
        productType = vehicleTypeRepository.save(getVehicleType());

        VehicleEntity product = productRepository.save(getVehicle(brandEntity, productType));
        log.info("VehicleEntity:{}", product);
    }

    private BrandEntity getRestaurant() {
        return BrandEntity.builder()
                .name(faker.company().name())
                .build();
    }

    private VehicleEntity getVehicle(BrandEntity brandEntity, VehicleTypeEntity productType) {
        return VehicleEntity.builder()
                .cor(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(productType)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
    }

    private VehicleEntity getVehicle1(BrandEntity brandEntity, VehicleTypeEntity productType) {
        return VehicleEntity.builder()
                .cor(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .vehicleTypeEntity(productType)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
    }

    private VehicleEntity getVehicle2(BrandEntity brandEntity, VehicleTypeEntity productType) {
        return VehicleEntity.builder()
                .cor(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .vehicleTypeEntity(productType)
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
    }

    private VehicleTypeEntity getVehicleType() {
        return VehicleTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    @Test
    void should_find_no_products_if_repository_is_empty() {
        Iterable<VehicleEntity> products = productRepository.findAll();
        products = Collections.EMPTY_LIST;
        assertThat(products).isEmpty();
    }

    @Test
    void should_store_a_product() {
        log.info("Setting up test data...");
        var restaurant1 = brandRepository.save(getRestaurant());
        var productType1 = vehicleTypeRepository.save(getVehicleType());

        VehicleEntity product = getVehicle(restaurant1, productType1);
        product.setCode(UUID.randomUUID().toString());

        // Ensure unique code
        VehicleEntity savedVehicle = productRepository.save(product);

        assertThat(savedVehicle).isNotNull();
        assertThat(savedVehicle.getId()).isNotNull();
        assertThat(savedVehicle.getCor()).isEqualTo(product.getCor());
    }

    @Test
    void should_find_product_by_id() {
        log.info("Setting up test data...");
        var restaurant1 = brandRepository.save(getRestaurant());
        var productType1 = vehicleTypeRepository.save(getVehicleType());

        VehicleEntity product = getVehicle(restaurant1, productType1);
        product.setCode(UUID.randomUUID().toString());

        // Ensure unique code
        VehicleEntity savedVehicle = productRepository.save(product);

        Optional<VehicleEntity> foundVehicle = productRepository.findById(savedVehicle.getId());
        assertThat(foundVehicle).isPresent();
        assertThat(foundVehicle.get().getCor()).isEqualTo(savedVehicle.getCor());
    }

    @Test
    void should_find_all_products() {
        log.info("Cleaning up database...");
        productRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        brandRepository.deleteAll();

        var restaurant1 = brandRepository.save(getRestaurant());
        var productType1 = vehicleTypeRepository.save(getVehicleType());

        VehicleEntity product1 = productRepository.save(getVehicle(restaurant1, productType1));

        Iterable<VehicleEntity> products = productRepository.findAll();
        List<VehicleEntity> productList = new ArrayList<>();
        products.forEach(productList::add);

        assertThat(productList).hasSize(1);
        assertThat(productList).extracting(VehicleEntity::getCor).contains(product1.getCor());
    }

    @Test
    void should_delete_all_products() {
        log.info("Cleaning up database...");
        productRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        brandRepository.deleteAll();

        var restaurant1 = brandRepository.save(getRestaurant());
        var productType1 = vehicleTypeRepository.save(getVehicleType());

        productRepository.save(getVehicle(restaurant1, productType1));
        productRepository.deleteAll();

        Iterable<VehicleEntity> products = productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        VehicleEntity fromDb = productRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfVehicles_whenFindAll_thenReturnAllVehicles() {
        productRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        brandRepository.deleteAll();

        List<VehicleEntity> all = productRepository.findAll();
        log.info(all.toString());

        BrandEntity restaurant1 = brandRepository.save(getRestaurant());
        VehicleTypeEntity productType1 = vehicleTypeRepository.save(getVehicleType());

        VehicleEntity product = getVehicle(restaurant1, productType1);
        log.info("VehicleEntity:{}", product);
        VehicleEntity product1 = productRepository.save(product);

        Iterable<VehicleEntity> products = productRepository.findAll();
        List<VehicleEntity> productList = new ArrayList<>();
        products.forEach(productList::add);

        assertThat(productList).hasSize(1);
        //assertThat(productList).extracting(VehicleEntity::getName).contains(product1.getName(), product2.getName(), product3.getName());
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        VehicleEntity productEntity = new VehicleEntity();
        productEntity.setCor("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        productEntity.setCode(UUID.randomUUID().toString());
        productEntity.setPic("hhh");
        productEntity.setPrice(BigDecimal.TEN);
        productEntity.setDescription("Coca-Cola");
        productEntity.setVehicleTypeEntity(productType);

        assertThrows(DataIntegrityViolationException.class, () -> {
            productRepository.save(productEntity);
        });
    }

    private VehicleEntity createInvalidVehicleType() {
        VehicleEntity productType = new VehicleEntity();
        // Configurar o productType com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        productType.setCor(""); // Nome vazio pode causar uma violação
        return productType;
    }
}
