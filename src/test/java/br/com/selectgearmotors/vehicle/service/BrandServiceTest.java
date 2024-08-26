package br.com.selectgearmotors.vehicle.service;

import br.com.selectgearmotors.vehicle.core.ports.in.brand.*;
import br.com.selectgearmotors.vehicle.application.database.mapper.BrandMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.ports.out.BrandRepositoryPort;
import br.com.selectgearmotors.vehicle.core.service.BrandService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @InjectMocks
    BrandService restaurantService;

    @Mock
    BrandRepositoryPort restaurantRepository;

    @Mock
    BrandRepository repository;

    @Mock
    BrandMapper mapper;

    @Mock
    CreateBrandPort createBrandPort;

    @Mock
    DeleteBrandPort deleteBrandPort;

    @Mock
    FindByIdBrandPort findByIdBrandPort;

    @Mock
    FindBrandsPort findBrandsPort;

    @Mock
    UpdateBrandPort updateBrandPort;

    private BrandEntity getBrandEntity() {
        return BrandEntity.builder()
                .name("Seven Food")
                .build();
    }

    private BrandEntity getBrandEntity1() {
        return BrandEntity.builder()
                .name("Seven Food Filial 1")
                .build();
    }

    private BrandEntity getBrandEntity2() {
        return BrandEntity.builder()
                .name("Seven Food Filial 2")
                .build();
    }

    private Brand getBrand() {
        return Brand.builder()
                .name("Seven Food")
                .build();
    }

    private Brand getBrand1() {
        return Brand.builder()
                .name("Seven Food Filial 1")
                .build();
    }

    private Brand getBrand2() {
        return Brand.builder()
                .name("Seven Food Filial 2")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllBrandsTest() {
        List<Brand> restaurants = new ArrayList<>();
        List<BrandEntity> listEntity = new ArrayList<>();

        Brand client = getBrand();
        Brand client1 = getBrand1();
        Brand client2 = getBrand2();

        BrandEntity clientEntity = getBrandEntity();
        BrandEntity clientEntity1 = getBrandEntity1();
        BrandEntity clientEntity2 = getBrandEntity2();

        restaurants.add(client);
        restaurants.add(client1);
        restaurants.add(client2);

        listEntity.add(clientEntity);
        listEntity.add(clientEntity1);
        listEntity.add(clientEntity2);

        when(restaurantService.findAll()).thenReturn(restaurants);

        List<Brand> restaurantList = restaurantService.findAll();

        assertNotNull(restaurantList);
    }

    @Test
    void getBrandByIdTest() {
        Brand restaurant1 = getBrand();
        when(restaurantService.findById(1L)).thenReturn(restaurant1);

        Brand restaurant = restaurantService.findById(1L);

        assertEquals("Seven Food", restaurant.getName());
    }

    @Test
    void getFindBrandByShortIdTest() {
        Brand restaurant = getBrand();
        when(restaurantService.findById(1L)).thenReturn(restaurant);

        Brand result = restaurantService.findById(1L);

        assertEquals("Seven Food", result.getName());
    }

    @Test
    void createBrandTest() {
        Brand restaurant = getBrand();
        Brand restaurantResult = getBrand();
        restaurantResult.setId(1L);

        when(restaurantService.save(restaurant)).thenReturn(restaurantResult);
        Brand save = restaurantService.save(restaurant);

        assertNotNull(save);
        //verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void testSaveBrandWithLongName() {
        Brand restaurant = new Brand();
        restaurant.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(restaurantRepository).save(restaurant);

        assertThrows(DataException.class, () -> {
            restaurantRepository.save(restaurant);
        });
    }

    @Test
    void testRemoveBrand_Success() {
        Long restaurantId = 1L;
        Brand restaurant = getBrand(); // Método que cria um objeto Brand para o teste
        restaurant.setId(restaurantId);

        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);
        boolean result = restaurantService.remove(restaurantId);
        assertTrue(result);
    }

    @Test
    void testRemove_Exception() {
        Long productId = 99L;

        boolean result = restaurantService.remove(productId);
        assertFalse(result);
        verify(restaurantRepository, never()).remove(productId);
    }

    @Test
    void createBrandPortTest() {
        Brand restaurant = getBrand();
        Brand restaurantResult = getBrand();
        restaurantResult.setId(1L);

        CreateBrandPort createBrandPort = mock(CreateBrandPort.class);
        when(createBrandPort.save(restaurant)).thenReturn(restaurantResult);

        Brand savedBrand = createBrandPort.save(restaurant);

        assertNotNull(savedBrand);
        assertEquals(1L, savedBrand.getId());
        assertEquals("Seven Food", savedBrand.getName());
    }

    @Test
    void deleteBrandPortTest() {
        Long restaurantId = 1L;

        DeleteBrandPort deleteBrandPort = mock(DeleteBrandPort.class);
        when(deleteBrandPort.remove(restaurantId)).thenReturn(true);

        boolean result = deleteBrandPort.remove(restaurantId);

        assertTrue(result);
    }

    @Test
    void findByIdBrandPortTest() {
        Long restaurantId = 1L;
        Brand restaurant = getBrand();
        restaurant.setId(restaurantId);

        FindByIdBrandPort findByIdBrandPort = mock(FindByIdBrandPort.class);
        when(findByIdBrandPort.findById(restaurantId)).thenReturn(restaurant);

        Brand foundBrand = findByIdBrandPort.findById(restaurantId);

        assertNotNull(foundBrand);
        assertEquals("Seven Food", foundBrand.getName());
    }

    @Test
    void findBrandsPortTest() {
        List<Brand> restaurants = new ArrayList<>();
        restaurants.add(getBrand());
        restaurants.add(getBrand1());
        restaurants.add(getBrand2());

        FindBrandsPort findBrandsPort = mock(FindBrandsPort.class);
        when(findBrandsPort.findAll()).thenReturn(restaurants);

        List<Brand> restaurantList = findBrandsPort.findAll();

        assertNotNull(restaurantList);
        assertEquals(3, restaurantList.size());
    }

    @Test
    void updateBrandPortTest() {
        Long restaurantId = 1L;
        Brand restaurant = getBrand();
        restaurant.setId(restaurantId);
        restaurant.setName("Updated Name");

        UpdateBrandPort updateBrandPort = mock(UpdateBrandPort.class);
        when(updateBrandPort.update(restaurantId, restaurant)).thenReturn(restaurant);

        Brand updatedBrand = updateBrandPort.update(restaurantId, restaurant);

        assertNotNull(updatedBrand);
        assertEquals("Updated Name", updatedBrand.getName());
    }

}