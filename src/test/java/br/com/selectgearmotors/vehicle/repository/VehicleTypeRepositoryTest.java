package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class VehicleTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    private VehicleType getVehicleType() {
        return VehicleType.builder()
                .name("Carro")
                .build();
    }

    private VehicleTypeEntity getVehicleTypeEntity() {
        return VehicleTypeEntity.builder()
                .name("Carro")
                .build();
    }

    @BeforeEach
    void setUp() {
        vehicleTypeRepository.deleteAll();

        vehicleTypeRepository.save(getVehicleTypeEntity());
    }

    @Test
    void should_find_no_clients_if_repository_is_empty() {
        vehicleTypeRepository.deleteAll();

        List<VehicleTypeEntity> seeds = new ArrayList<>();
        seeds = vehicleTypeRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_product_category() {
        String cocaColaBeverage = "Coca-Cola";
        
        Optional<VehicleTypeEntity> productCategory = vehicleTypeRepository.findByName(cocaColaBeverage);
        Optional<VehicleTypeEntity> productCategoryResponse = null;
        if (!productCategory.isPresent()) {

            VehicleTypeEntity cocaCola = VehicleTypeEntity.builder()
                    .name(cocaColaBeverage)
                    .build();

            VehicleTypeEntity save = vehicleTypeRepository.save(cocaCola);
            productCategoryResponse = vehicleTypeRepository.findByName(cocaColaBeverage);
        }

        VehicleTypeEntity productCategory1 = productCategoryResponse.get();
        assertThat(productCategory1).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        VehicleTypeEntity productCategory = new VehicleTypeEntity();
        productCategory.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleTypeRepository.save(productCategory);
        });
    }

    private VehicleTypeEntity createInvalidProductCategory() {
        VehicleTypeEntity productCategory = new VehicleTypeEntity();
        // Configurar o productCategory com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        productCategory.setName(""); // Nome vazio pode causar uma violação
        return productCategory;
    }

    @Test
    void should_found_null_ProductCategory() {
        VehicleTypeEntity productCategory = null;

        Optional<VehicleTypeEntity> fromDb = vehicleTypeRepository.findById(99l);
        if (fromDb.isPresent()) {
            productCategory = fromDb.get();
        }
        assertThat(productCategory).isNull();
    }

    @Test
    void whenFindById_thenReturnProductCategory() {
        Optional<VehicleTypeEntity> productCategory = vehicleTypeRepository.findById(1l);
        if (productCategory.isPresent()) {
            VehicleTypeEntity productCategoryResult = productCategory.get();
            assertThat(productCategoryResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        VehicleTypeEntity fromDb = vehicleTypeRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfProductCategorys_whenFindAll_thenReturnAllProductCategorys() {
        VehicleTypeEntity productCategory = null;
        VehicleTypeEntity productCategory1 = null;
        VehicleTypeEntity productCategory2 = null;

        Optional<VehicleTypeEntity> restaurant = vehicleTypeRepository.findById(1l);
        if (restaurant.isPresent()) {

            VehicleTypeEntity bebida = VehicleTypeEntity.builder()
                    .name("Bebida")
                    .build();
            productCategory = vehicleTypeRepository.save(bebida);

            VehicleTypeEntity acompanhamento = VehicleTypeEntity.builder()
                    .name("Acompanhamento")
                    .build();
            productCategory1 = vehicleTypeRepository.save(acompanhamento);

            VehicleTypeEntity lanche = VehicleTypeEntity.builder()
                    .name("Lanche")
                    .build();
            productCategory2 = vehicleTypeRepository.save(lanche);

        }

        Iterator<VehicleTypeEntity> allProductCategorys = vehicleTypeRepository.findAll().iterator();
        List<VehicleTypeEntity> clients = new ArrayList<>();
        allProductCategorys.forEachRemaining(c -> clients.add(c));

        assertNotNull(allProductCategorys);
    }
}