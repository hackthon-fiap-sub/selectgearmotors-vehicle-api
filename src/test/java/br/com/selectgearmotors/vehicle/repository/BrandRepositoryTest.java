package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

    private BrandEntity brandEntity;

    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        brandRepository.deleteAll();

        brandEntity = BrandEntity.builder()
                .name(faker.company().name())
                .build();

        brandEntity = brandRepository.save(brandEntity);
    }

    @Test
    void should_find_no_restaurants_if_repository_is_empty() { //TODO - Refatorar
        brandRepository.deleteAll();
        Iterable<BrandEntity> seeds = brandRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_restaurant() {
        String nomeFilial = faker.company().name();
        Optional<BrandEntity> restaurant = brandRepository.findByName(nomeFilial);

        if (!restaurant.isPresent()) {
            BrandEntity restaurant2 = BrandEntity.builder()
                    .name(nomeFilial)
                    .build();

            BrandEntity savedBrand = brandRepository.save(restaurant2);
            Optional<BrandEntity> brandResponse = brandRepository.findByName(nomeFilial);

            BrandEntity brand1 = brandResponse.orElse(null);
            assertThat(brand1).isNotNull();
            assertThat(brand1).hasFieldOrPropertyWithValue("name", nomeFilial);
        }
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        BrandEntity restaurant = new BrandEntity();
        restaurant.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        assertThrows(DataIntegrityViolationException.class, () -> {
            brandRepository.save(restaurant);
        });
    }

    private BrandEntity createInvalidRestaurant() {
        return BrandEntity.builder()
                .name("") // Nome vazio pode causar uma violação
                .build();
    }

    private BrandEntity createInvalidRestaurantGt255() {
        return BrandEntity.builder()
                .name(generateLongName(260)) // Nome vazio pode causar uma violação
                .build();
    }

    @Test
    void should_find_null_restaurant() {
        Optional<BrandEntity> fromDb = brandRepository.findById(99L);
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnRestaurant() {
        brandRepository.deleteAll();

        String nameCompany = faker.company().name();
        brandEntity = BrandEntity.builder()
                .name(nameCompany)
                .build();

        brandEntity = brandRepository.save(brandEntity);

        Optional<BrandEntity> restaurant = brandRepository.findById(brandEntity.getId());
        assertThat(restaurant).isPresent();
        restaurant.ifPresent(restaurantResult -> assertThat(restaurantResult).hasFieldOrPropertyWithValue("name", nameCompany));
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        BrandEntity fromDb = brandRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfRestaurants_whenFindAll_thenReturnAllRestaurants() {
        brandRepository.deleteAll();

        String nameCompany1 = faker.company().name();
        String nameCompany2 = faker.company().name();

        BrandEntity restaurant1 = BrandEntity.builder()
                .name(nameCompany1)
                .build();

        BrandEntity restaurant2 = BrandEntity.builder()
                .name(nameCompany2)
                .build();

        brandRepository.saveAll(Arrays.asList(restaurant1, restaurant2));

        Iterable<BrandEntity> allRestaurants = brandRepository.findAll();
        List<BrandEntity> restaurantList = new ArrayList<>();
        allRestaurants.forEach(restaurantList::add);

        assertThat(restaurantList).hasSize(2).extracting(BrandEntity::getName)
                .containsExactlyInAnyOrder(nameCompany1, nameCompany2);
    }

    private String generateLongName(int length) {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < length; i++) {
            name.append('a'); // Adiciona o caractere 'a' repetidamente
        }
        return name.toString();
    }
}
