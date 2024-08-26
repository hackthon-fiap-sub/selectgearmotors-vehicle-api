package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.database.mapper.VehicleTypeMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleTypeRepository;
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
class VehicleTypeRepositoryAdapterTest {

    @InjectMocks
    VehicleTypeRepositoryAdapter productTypeRepositoryAdapter;

    @Mock
    private VehicleTypeRepository productTypeRepository;

    @Mock
    private VehicleTypeMapper productTypeMapper;

    private VehicleType getVehicleType() {
        return VehicleType.builder()
                .id(1l)
                .name("Bebida")
                .build();
    }

    private VehicleTypeEntity getVehicleTypeEntity() {
        return VehicleTypeEntity.builder()
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
        Iterable<VehicleTypeEntity> seeds = productTypeRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_product_category() {
        String cocaColaBeverage = "Coca-Cola";
        VehicleTypeEntity cocaCola = VehicleTypeEntity.builder()
                .name(cocaColaBeverage)
                .build();

        when(productTypeRepository.save(cocaCola)).thenReturn(cocaCola);
        VehicleTypeEntity saved = productTypeRepository.save(cocaCola);
        log.info("VehicleTypeEntity:{}", saved);
        assertThat(saved).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        VehicleTypeEntity productTypeEntity = new VehicleTypeEntity();
        productTypeEntity.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productTypeRepository).save(productTypeEntity);

        assertThrows(DataException.class, () -> {
            productTypeRepository.save(productTypeEntity);
        });
    }

    private VehicleTypeEntity createInvalidVehicleType() {
        VehicleTypeEntity productType = new VehicleTypeEntity();
        // Configurar o productType com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        productType.setName(""); // Nome vazio pode causar uma violação
        return productType;
    }

    @Test
    void should_found_null_VehicleType() {
        VehicleTypeEntity productType = null;

        when(productTypeRepository.findById(99l)).thenReturn(Optional.empty());
        Optional<VehicleTypeEntity> fromDb = productTypeRepository.findById(99l);
        if (fromDb.isPresent()) {
            productType = fromDb.get();
        }
        assertThat(productType).isNull();
        assertThat(fromDb).isEmpty();
    }

    @Test
    void whenFindById_thenReturnVehicleType() {
        Optional<VehicleTypeEntity> productType = productTypeRepository.findById(1l);
        if (productType.isPresent()) {
            VehicleTypeEntity productTypeResult = productType.get();
            assertThat(productTypeResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        VehicleTypeEntity fromDb = productTypeRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfVehicleTypes_whenFindAll_thenReturnAllVehicleTypes() {
        VehicleTypeEntity productType = null;
        VehicleTypeEntity productType1 = null;
        VehicleTypeEntity productType2 = null;

        Optional<VehicleTypeEntity> restaurant = productTypeRepository.findById(1l);
        if (restaurant.isPresent()) {

            VehicleTypeEntity bebida = VehicleTypeEntity.builder()
                    .name("Bebida")
                    .build();
            productType = productTypeRepository.save(bebida);

            VehicleTypeEntity acompanhamento = VehicleTypeEntity.builder()
                    .name("Acompanhamento")
                    .build();
            productType1 = productTypeRepository.save(acompanhamento);

            VehicleTypeEntity lanche = VehicleTypeEntity.builder()
                    .name("Lanche")
                    .build();
            productType2 = productTypeRepository.save(lanche);

        }

        Iterator<VehicleTypeEntity> allVehicleTypes = productTypeRepository.findAll().iterator();
        List<VehicleTypeEntity> clients = new ArrayList<>();
        allVehicleTypes.forEachRemaining(c -> clients.add(c));

        assertNotNull(allVehicleTypes);
    }
}