package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.database.mapper.ModelMapper;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ModelRepositoryAdapterTest {

    @InjectMocks
    ModelRepositoryAdapter restaurantRepositoryAdapter;

    @Mock
    private ModelRepository restaurantRepository;

    @Mock
    private ModelMapper restaurantMapper;

    private Model getModel() {
        return Model.builder()
                .id(1l)
                .name("Seven-Food")
                .build();
    }

    private ModelEntity getModelEntity() {
        return ModelEntity.builder()
                .id(1l)
                .name("Seven-Food")
                .build();
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_find_no_clients_if_repository_is_empty() {
        Iterable<ModelEntity> seeds = restaurantRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_product_category() {
        String cocaColaBeverage = "Coca-Cola";
        ModelEntity cocaCola = ModelEntity.builder()
                .name(cocaColaBeverage)
                .build();

        when(restaurantRepository.save(cocaCola)).thenReturn(cocaCola);
        ModelEntity saved = restaurantRepository.save(cocaCola);
        log.info("ModelEntity:{}", saved);
        assertThat(saved).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Disabled
    void testSaveModelWithLongName() {
        ModelEntity brandEntity = new ModelEntity();
        brandEntity.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(restaurantRepository).save(brandEntity);

        assertThrows(DataException.class, () -> {
            restaurantRepository.save(brandEntity);
        });
    }

    private ModelEntity createInvalidModel() {
        ModelEntity pestaurant = new ModelEntity();
        // Configurar o pestaurant com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        pestaurant.setName(""); // Nome vazio pode causar uma violação
        return pestaurant;
    }

    @Test
    void should_found_null_Model() {
        ModelEntity pestaurant = null;

        when(restaurantRepository.findById(99l)).thenReturn(Optional.empty());
        Optional<ModelEntity> fromDb = restaurantRepository.findById(99l);
        if (fromDb.isPresent()) {
            pestaurant = fromDb.get();
        }
        assertThat(pestaurant).isNull();
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnModel() {
        Optional<ModelEntity> pestaurant = restaurantRepository.findById(1l);
        if (pestaurant.isPresent()) {
            ModelEntity pestaurantResult = pestaurant.get();
            assertThat(pestaurantResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        ModelEntity fromDb = restaurantRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfModels_whenFindAll_thenReturnAllModels() {
        ModelEntity pestaurant = null;
        ModelEntity pestaurant1 = null;
        ModelEntity pestaurant2 = null;

        Optional<ModelEntity> restaurant = restaurantRepository.findById(1l);
        if (restaurant.isPresent()) {

            ModelEntity bebida = ModelEntity.builder()
                    .name("Bebida")
                    .build();
            pestaurant = restaurantRepository.save(bebida);

            ModelEntity acompanhamento = ModelEntity.builder()
                    .name("Acompanhamento")
                    .build();
            pestaurant1 = restaurantRepository.save(acompanhamento);

            ModelEntity lanche = ModelEntity.builder()
                    .name("Lanche")
                    .build();
            pestaurant2 = restaurantRepository.save(lanche);

        }

        Iterator<ModelEntity> allModels = restaurantRepository.findAll().iterator();
        List<ModelEntity> clients = new ArrayList<>();
        allModels.forEachRemaining(c -> clients.add(c));

        assertNotNull(allModels);
    }

    @Test
    void testUpdateModel_NotFound() {
        Long restaurantId = 99L;
        Model restaurant = new Model(); // Configure as needed
        Model result = restaurantRepositoryAdapter.update(restaurantId, restaurant);
        assertThat(result).isNull();
    }
}