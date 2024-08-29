package br.com.selectgearmotors.vehicle.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.BrandRequest;
import br.com.selectgearmotors.vehicle.application.api.mapper.BrandApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.service.BrandService;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import br.com.selectgearmotors.vehicle.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class BrandResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BrandService service;

    @Autowired
    private BrandRepository repository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Mock
    private BrandApiMapper productApiMapper;

    private Brand restaurant;

    private Brand getBrand() {
        return Brand.builder()
                .name("Seven Food - Filial")
                .build();
    }

    private Brand getBrandUpdate() {
        return Brand.builder()
                .id(1l)
                .name("Seven Food - Filial")
                .build();
    }

    @BeforeEach
    void setUp() {
        vehicleRepository.deleteAll();
        modelRepository.deleteAll();
        repository.deleteAll();
        this.restaurant = service.save(getBrand());
    }

    @Test
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/brands/{id}", this.restaurant.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Seven Food - Filial"));
    }

    @Test
    void getAll() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/brands")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }


    @Test
    void create() throws Exception {
        Brand restaurantToSave = getBrand();
        String create = JsonUtil.getJson(restaurantToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/brands")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Disabled
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new Brand());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/brands")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testSave_Exception() throws Exception {
        BrandRequest restaurantRequest = new BrandRequest();
        String create = JsonUtil.getJson(restaurantRequest);

        when(productApiMapper.fromRequest(restaurantRequest)).thenThrow(new RuntimeException("Brande não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/brands")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void update() throws Exception {
        repository.deleteAll();
        Brand savedBrand = service.save(getBrandUpdate());
        Long id = savedBrand.getId();
        String update = JsonUtil.getJson(savedBrand);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/brands/{id}", id)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Seven Food - Filial"));
    }

    @Disabled
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new Brand());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/brands/{id}", 99L)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testUpdate_Exception() throws Exception {
        BrandRequest product = new BrandRequest();
        String create = JsonUtil.getJson(product);

        when(productApiMapper.fromRequest(product)).thenThrow(new RuntimeException("Brande não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/brands/{id}", 99L)
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void deleteBrandAPI() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.delete("/v1/brands/{id}", 1) )
                .andExpect(status().isNoContent());
    }

    @Disabled
    void findById_productIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/brands/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        BrandRequest restaurantRequest = new BrandRequest();
        when(productApiMapper.fromRequest(restaurantRequest)).thenThrow(new RuntimeException("Brande não encontrado ao buscar por id"));

        MvcResult result = mockMvc.perform(get("/v1/brands/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}