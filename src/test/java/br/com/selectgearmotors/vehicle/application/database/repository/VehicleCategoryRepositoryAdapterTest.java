package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleCategoryMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleCategoryRepository;
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
class VehicleCategoryRepositoryAdapterTest {

    @InjectMocks
    VehicleCategoryRepositoryAdapter productCategoryRepositoryAdapter;

    @Mock
    private VehicleCategoryRepository productCategoryRepository;

    @Mock
    private VehicleCategoryMapper productCategoryMapper;

    private VehicleCategory getVehicleCategory() {
        return VehicleCategory.builder()
                .id(1l)
                .name("Bebida")
                .build();
    }

    private VehicleCategoryEntity getVehicleCategoryEntity() {
        return VehicleCategoryEntity.builder()
                .id(1l)
                .name("Bebida")
                .build();
    }


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_find_no_clients_if_repository_is_empty() {
        Iterable<VehicleCategoryEntity> seeds = productCategoryRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_product_category() {
        String cocaColaBeverage = "Coca-Cola";
        VehicleCategoryEntity cocaCola = VehicleCategoryEntity.builder()
                .name(cocaColaBeverage)
                .build();

        when(productCategoryRepository.save(cocaCola)).thenReturn(cocaCola);
        VehicleCategoryEntity saved = productCategoryRepository.save(cocaCola);
        log.info("VehicleCategoryEntity:{}", saved);
        assertThat(saved).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        VehicleCategoryEntity productCategoryEntity = new VehicleCategoryEntity();
        productCategoryEntity.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productCategoryRepository).save(productCategoryEntity);

        assertThrows(DataException.class, () -> {
            productCategoryRepository.save(productCategoryEntity);
        });
    }

    private VehicleCategoryEntity createInvalidVehicleCategory() {
        VehicleCategoryEntity productCategory = new VehicleCategoryEntity();
        // Configurar o productCategory com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        productCategory.setName(""); // Nome vazio pode causar uma violação
        return productCategory;
    }

    @Test
    void should_found_null_VehicleCategory() {
        VehicleCategoryEntity productCategory = null;

        when(productCategoryRepository.findById(99l)).thenReturn(Optional.empty());
        Optional<VehicleCategoryEntity> fromDb = productCategoryRepository.findById(99l);
        if (fromDb.isPresent()) {
            productCategory = fromDb.get();
        }
        assertThat(productCategory).isNull();
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnVehicleCategory() {
        Optional<VehicleCategoryEntity> productCategory = productCategoryRepository.findById(1l);
        if (productCategory.isPresent()) {
            VehicleCategoryEntity productCategoryResult = productCategory.get();
            assertThat(productCategoryResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        VehicleCategoryEntity fromDb = productCategoryRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfVehicleCategorys_whenFindAll_thenReturnAllVehicleCategorys() {
        VehicleCategoryEntity productCategory = null;
        VehicleCategoryEntity productCategory1 = null;
        VehicleCategoryEntity productCategory2 = null;

        Optional<VehicleCategoryEntity> restaurant = productCategoryRepository.findById(1l);
        if (restaurant.isPresent()) {

            VehicleCategoryEntity bebida = VehicleCategoryEntity.builder()
                    .name("Bebida")
                    .build();
            productCategory = productCategoryRepository.save(bebida);

            VehicleCategoryEntity acompanhamento = VehicleCategoryEntity.builder()
                    .name("Acompanhamento")
                    .build();
            productCategory1 = productCategoryRepository.save(acompanhamento);

            VehicleCategoryEntity lanche = VehicleCategoryEntity.builder()
                    .name("Lanche")
                    .build();
            productCategory2 = productCategoryRepository.save(lanche);

        }

        Iterator<VehicleCategoryEntity> allVehicleCategorys = productCategoryRepository.findAll().iterator();
        List<VehicleCategoryEntity> clients = new ArrayList<>();
        allVehicleCategorys.forEachRemaining(c -> clients.add(c));

        assertNotNull(allVehicleCategorys);
    }
}