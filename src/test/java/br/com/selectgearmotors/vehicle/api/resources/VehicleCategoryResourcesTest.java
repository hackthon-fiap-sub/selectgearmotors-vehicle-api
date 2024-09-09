package br.com.selectgearmotors.vehicle.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleCategoryRequest;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleCategoryApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.service.VehicleCategoryService;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleCategoryRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class VehicleCategoryResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VehicleCategoryService service;

    @Autowired
    private VehicleCategoryRepository repository;

    @Autowired
    private VehicleRepository vehicleRepository;

    private VehicleCategory vehicleCategory;

    private Long productCategoryId;

    @Mock
    private VehicleCategoryApiMapper productCategoryApiMapper;

    private VehicleCategory getVehicleCategory() {
        return VehicleCategory.builder()
                .name("Bebida")
                .build();
    }

    private VehicleCategory getVehicleCategoryUpdate() {
        return VehicleCategory.builder()
                .name("Bebida1")
                .build();
    }

    @BeforeEach
    void setUp() {
        vehicleRepository.deleteAll();
        repository.deleteAll();

        this.vehicleCategory = service.save(getVehicleCategory());
        this.productCategoryId = vehicleCategory.getId();
    }

    @Disabled
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/vehicle-categories/{id}", this.productCategoryId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bebida"));
    }

    @Disabled
    void getAll() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/vehicle-categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }

    @Disabled
    void getAll_isNull() throws Exception {
        repository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/vehicle-categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        String create = JsonUtil.getJson(getVehicleCategory());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/vehicle-categories")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Disabled
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new VehicleCategory());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/vehicle-categories")
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
        VehicleCategoryRequest productCategoryRequest = new VehicleCategoryRequest();
        String create = JsonUtil.getJson(productCategoryRequest);

        when(productCategoryApiMapper.fromRequest(productCategoryRequest)).thenThrow(new RuntimeException("Produto não encontroado ao cadastrar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/vehicle-categories")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void update() throws Exception {
        repository.deleteAll();
        VehicleCategory savedVehicleCategory = service.save(getVehicleCategory());
        Long id = savedVehicleCategory.getId();
        String update = JsonUtil.getJson(getVehicleCategoryUpdate());

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/v1/vehicle-categories/{id}", id)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bebida1"));
    }

    @Disabled
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new VehicleCategoryRequest());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/vehicle-categories/{id}", productCategoryId)
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
        VehicleCategoryRequest product = new VehicleCategoryRequest();
        String create = JsonUtil.getJson(product);

        when(productCategoryApiMapper.fromRequest(product)).thenThrow(new RuntimeException("Produto não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/vehicle-categories/{id}", productCategoryId)
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void delete() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.delete("/v1/vehicle-categories/{id}", 1) )
                .andExpect(status().isNoContent());
    }

    @Disabled
    void findByCode_productIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/vehicle-categories/{id}", 99l))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        VehicleCategoryRequest productCategoryRequest = new VehicleCategoryRequest();
        when(productCategoryApiMapper.fromRequest(productCategoryRequest)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por código"));

        MvcResult result = mockMvc.perform(get("/v1/vehicle-categories/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}