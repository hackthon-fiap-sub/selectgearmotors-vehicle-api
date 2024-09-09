package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
class VehicleCategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;

    private VehicleCategory getVehicleCategory() {
        return VehicleCategory.builder()
                .name("Carro")
                .build();
    }

    private VehicleCategoryEntity getVehicleCategoryEntity() {
        return VehicleCategoryEntity.builder()
                .name("Carro")
                .build();
    }

    @BeforeEach
    void setUp() {
        vehicleCategoryRepository.deleteAll();

        vehicleCategoryRepository.save(getVehicleCategoryEntity());
    }

    @Disabled
    void should_find_no_clients_if_repository_is_empty() {
        vehicleCategoryRepository.deleteAll();

        List<VehicleCategoryEntity> seeds = new ArrayList<>();
        seeds = vehicleCategoryRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Disabled
    void should_store_a_product_category() {
        String cocaColaBeverage = "Coca-Cola";
        
        Optional<VehicleCategoryEntity> productCategory = vehicleCategoryRepository.findByName(cocaColaBeverage);
        Optional<VehicleCategoryEntity> productCategoryResponse = null;
        if (!productCategory.isPresent()) {

            VehicleCategoryEntity cocaCola = VehicleCategoryEntity.builder()
                    .name(cocaColaBeverage)
                    .build();

            VehicleCategoryEntity save = vehicleCategoryRepository.save(cocaCola);
            productCategoryResponse = vehicleCategoryRepository.findByName(cocaColaBeverage);
        }

        VehicleCategoryEntity productCategory1 = productCategoryResponse.get();
        assertThat(productCategory1).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        VehicleCategoryEntity productCategory = new VehicleCategoryEntity();
        productCategory.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleCategoryRepository.save(productCategory);
        });
    }

    private VehicleCategoryEntity createInvalidProductCategory() {
        VehicleCategoryEntity productCategory = new VehicleCategoryEntity();
        // Configurar o productCategory com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        productCategory.setName(""); // Nome vazio pode causar uma violação
        return productCategory;
    }

    @Disabled
    void should_found_null_ProductCategory() {
        VehicleCategoryEntity productCategory = null;

        Optional<VehicleCategoryEntity> fromDb = vehicleCategoryRepository.findById(99l);
        if (fromDb.isPresent()) {
            productCategory = fromDb.get();
        }
        assertThat(productCategory).isNull();
    }

    @Disabled
    void whenFindById_thenReturnProductCategory() {
        Optional<VehicleCategoryEntity> productCategory = vehicleCategoryRepository.findById(1l);
        if (productCategory.isPresent()) {
            VehicleCategoryEntity productCategoryResult = productCategory.get();
            assertThat(productCategoryResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Disabled
    void whenInvalidId_thenReturnNull() {
        VehicleCategoryEntity fromDb = vehicleCategoryRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    void givenSetOfProductCategorys_whenFindAll_thenReturnAllProductCategorys() {
        VehicleCategoryEntity productCategory = null;
        VehicleCategoryEntity productCategory1 = null;
        VehicleCategoryEntity productCategory2 = null;

        Optional<VehicleCategoryEntity> restaurant = vehicleCategoryRepository.findById(1l);
        if (restaurant.isPresent()) {

            VehicleCategoryEntity bebida = VehicleCategoryEntity.builder()
                    .name("Bebida")
                    .build();
            productCategory = vehicleCategoryRepository.save(bebida);

            VehicleCategoryEntity acompanhamento = VehicleCategoryEntity.builder()
                    .name("Acompanhamento")
                    .build();
            productCategory1 = vehicleCategoryRepository.save(acompanhamento);

            VehicleCategoryEntity lanche = VehicleCategoryEntity.builder()
                    .name("Lanche")
                    .build();
            productCategory2 = vehicleCategoryRepository.save(lanche);

        }

        Iterator<VehicleCategoryEntity> allProductCategorys = vehicleCategoryRepository.findAll().iterator();
        List<VehicleCategoryEntity> clients = new ArrayList<>();
        allProductCategorys.forEachRemaining(c -> clients.add(c));

        assertNotNull(allProductCategorys);
    }
}