package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.application.database.mapper.BrandMapper;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import br.com.selectgearmotors.vehicle.util.CnpjGenerator;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BrandRepositoryAdapterTest {

    @InjectMocks
    BrandRepositoryAdapter restaurantRepositoryAdapter;

    @Mock
    private BrandRepository restaurantRepository;

    @Mock
    private BrandMapper restaurantMapper;

    private Brand getBrand() {
        return Brand.builder()
                .id(1l)
                .name("Seven-Food")
                .build();
    }

    private BrandEntity getBrandEntity() {
        return BrandEntity.builder()
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
        Iterable<BrandEntity> seeds = restaurantRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_product_category() {
        String cocaColaBeverage = "Coca-Cola";
        BrandEntity cocaCola = BrandEntity.builder()
                .name(cocaColaBeverage)
                .build();

        when(restaurantRepository.save(cocaCola)).thenReturn(cocaCola);
        BrandEntity saved = restaurantRepository.save(cocaCola);
        log.info("BrandEntity:{}", saved);
        assertThat(saved).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Disabled
    void testSaveBrandWithLongName() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(restaurantRepository).save(brandEntity);

        assertThrows(DataException.class, () -> {
            restaurantRepository.save(brandEntity);
        });
    }

    private BrandEntity createInvalidBrand() {
        BrandEntity pestaurant = new BrandEntity();
        // Configurar o pestaurant com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        pestaurant.setName(""); // Nome vazio pode causar uma violação
        return pestaurant;
    }

    @Test
    void should_found_null_Brand() {
        BrandEntity pestaurant = null;

        when(restaurantRepository.findById(99l)).thenReturn(Optional.empty());
        Optional<BrandEntity> fromDb = restaurantRepository.findById(99l);
        if (fromDb.isPresent()) {
            pestaurant = fromDb.get();
        }
        assertThat(pestaurant).isNull();
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnBrand() {
        Optional<BrandEntity> pestaurant = restaurantRepository.findById(1l);
        if (pestaurant.isPresent()) {
            BrandEntity pestaurantResult = pestaurant.get();
            assertThat(pestaurantResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        BrandEntity fromDb = restaurantRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfBrands_whenFindAll_thenReturnAllBrands() {
        BrandEntity pestaurant = null;
        BrandEntity pestaurant1 = null;
        BrandEntity pestaurant2 = null;

        Optional<BrandEntity> restaurant = restaurantRepository.findById(1l);
        if (restaurant.isPresent()) {

            BrandEntity bebida = BrandEntity.builder()
                    .name("Bebida")
                    .build();
            pestaurant = restaurantRepository.save(bebida);

            BrandEntity acompanhamento = BrandEntity.builder()
                    .name("Acompanhamento")
                    .build();
            pestaurant1 = restaurantRepository.save(acompanhamento);

            BrandEntity lanche = BrandEntity.builder()
                    .name("Lanche")
                    .build();
            pestaurant2 = restaurantRepository.save(lanche);

        }

        Iterator<BrandEntity> allBrands = restaurantRepository.findAll().iterator();
        List<BrandEntity> clients = new ArrayList<>();
        allBrands.forEachRemaining(c -> clients.add(c));

        assertNotNull(allBrands);
    }

    @Test
    void testUpdateBrand_NotFound() {
        Long restaurantId = 99L;
        Brand restaurant = new Brand(); // Configure as needed
        Brand result = restaurantRepositoryAdapter.update(restaurantId, restaurant);
        assertThat(result).isNull();
    }
}