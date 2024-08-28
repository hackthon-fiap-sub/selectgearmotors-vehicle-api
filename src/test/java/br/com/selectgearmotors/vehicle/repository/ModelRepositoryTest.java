package br.com.selectgearmotors.vehicle.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
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
class ModelRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandRepository brandRepository;

    private ModelEntity modelEntity;

    private BrandEntity brandEntity;

    private String modelName;

    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        brandRepository.deleteAll();
        modelRepository.deleteAll();

        this.brandEntity = brandRepository.save(new BrandEntity(1l, "Ford"));

        this.modelName = faker.company().name();
        var modelEntityToSave = ModelEntity.builder()
                .name(modelName)
                .brandEntity(this.brandEntity)
                .build();

        this.modelEntity = modelRepository.save(modelEntityToSave);
    }

    @Test
    void should_find_no_models_if_repository_is_empty() { //TODO - Refatorar
        modelRepository.deleteAll();
        Iterable<ModelEntity> seeds = modelRepository.findAll();
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_model() {
        String name = modelEntity.getName();
        Optional<ModelEntity> modelResponse = modelRepository.findByName(name);

        ModelEntity model1 = modelResponse.orElse(null);
        assertThat(model1).isNotNull();
        assertThat(model1).hasFieldOrPropertyWithValue("name", name);
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        ModelEntity model = new ModelEntity();
        model.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        assertThrows(DataIntegrityViolationException.class, () -> {
            modelRepository.save(model);
        });
    }

    private ModelEntity createInvalidRestaurant() {
        return ModelEntity.builder()
                .name("") // Nome vazio pode causar uma violação
                .build();
    }

    private ModelEntity createInvalidRestaurantGt255() {
        return ModelEntity.builder()
                .name(generateLongName(260)) // Nome vazio pode causar uma violação
                .build();
    }

    @Test
    void should_find_null_model() {
        Optional<ModelEntity> fromDb = modelRepository.findById(99L);
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnModel() {
        Optional<ModelEntity> modelEntityOP = modelRepository.findById(this.modelEntity.getId());
        assertThat(modelEntityOP).isPresent();
        modelEntityOP.ifPresent(modelResult -> assertThat(modelResult).hasFieldOrPropertyWithValue("name", this.modelName));
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        ModelEntity fromDb = modelRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfRestaurants_whenFindAll_thenReturnAllRestaurants() {
        modelRepository.deleteAll();

        String nameCompany1 = faker.company().name();
        String nameCompany2 = faker.company().name();

        ModelEntity model1 = ModelEntity.builder()
                .name(nameCompany1)
                .brandEntity(this.brandEntity)
                .build();

        ModelEntity model2 = ModelEntity.builder()
                .name(nameCompany2)
                .brandEntity(this.brandEntity)
                .build();

        modelRepository.saveAll(Arrays.asList(model1, model2));

        Iterable<ModelEntity> allRestaurants = modelRepository.findAll();
        List<ModelEntity> modelList = new ArrayList<>();
        allRestaurants.forEach(modelList::add);

        assertThat(modelList).hasSize(2).extracting(ModelEntity::getName)
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
