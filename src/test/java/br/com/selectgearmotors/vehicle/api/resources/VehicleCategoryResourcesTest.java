package br.com.selectgearmotors.vehicle.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleTypeRequest;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleTypeApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.service.VehicleTypeService;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleTypeRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import br.com.selectgearmotors.vehicle.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
class VehicleTypeResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VehicleTypeService service;

    @Autowired
    private VehicleTypeRepository repository;

    @Autowired
    private VehicleRepository productRepository;

    private VehicleType vehicleType;

    private Long productTypeId;

    @Mock
    private VehicleTypeApiMapper productTypeApiMapper;

    private VehicleType getVehicleType() {
        return VehicleType.builder()
                .name("Bebida")
                .build();
    }

    private VehicleType getVehicleTypeUpdate() {
        return VehicleType.builder()
                .name("Bebida1")
                .build();
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        repository.deleteAll();
        this.vehicleType = service.save(getVehicleType());
        this.productTypeId = vehicleType.getId();
    }

    @Test
    void findsTaskById() throws Exception {
        Long id = vehicleType.getId();
        mockMvc.perform(get("/v1/product-categories/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bebida"));
    }

    @Test
    void getAll() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/product-categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }

    @Test
    void getAll_isNull() throws Exception {
        repository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/product-categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void create() throws Exception {
        String create = JsonUtil.getJson(getVehicleType());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/product-categories")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new VehicleType());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/product-categories")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void testSave_Exception() throws Exception {
        VehicleTypeRequest productTypeRequest = new VehicleTypeRequest();
        String create = JsonUtil.getJson(productTypeRequest);

        when(productTypeApiMapper.fromRequest(productTypeRequest)).thenThrow(new RuntimeException("Produto não encontroado ao cadastrar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/product-categories")
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
        VehicleType savedVehicleType = service.save(getVehicleType());
        Long id = savedVehicleType.getId();
        String update = JsonUtil.getJson(getVehicleTypeUpdate());

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/v1/product-categories/{id}", id)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bebida1"));
    }

    @Test
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new VehicleTypeRequest());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/product-categories/{id}", productTypeId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void testUpdate_Exception() throws Exception {
        VehicleTypeRequest product = new VehicleTypeRequest();
        String create = JsonUtil.getJson(product);

        when(productTypeApiMapper.fromRequest(product)).thenThrow(new RuntimeException("Produto não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/product-categories/{id}", productTypeId)
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.delete("/v1/product-categories/{id}", 1) )
                .andExpect(status().isNoContent());
    }

    @Test
    void findByCode_productIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/product-categories/{id}", 99l))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Test
    void testById_Exception() throws Exception {
        VehicleTypeRequest productTypeRequest = new VehicleTypeRequest();
        when(productTypeApiMapper.fromRequest(productTypeRequest)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por código"));

        MvcResult result = mockMvc.perform(get("/v1/product-categories/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}