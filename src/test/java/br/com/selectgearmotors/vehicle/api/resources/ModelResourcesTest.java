package br.com.selectgearmotors.vehicle.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.ModelRequest;
import br.com.selectgearmotors.vehicle.application.api.mapper.ModelApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.service.ModelService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
import br.com.selectgearmotors.vehicle.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
class ModelResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(ModelResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ModelService service;

    @Autowired
    private ModelRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    @Mock
    private ModelApiMapper productApiMapper;

    private Model model;

    private BrandEntity brandEntity;

    private Model getModel(BrandEntity brand) {
        return Model.builder()
                .name("Seven Food - Filial")
                .brandId(brand.getId())
                .build();
    }

    private Model getModelUpdate(BrandEntity brand) {
        return Model.builder()
                .id(1l)
                .name("Seven Food - Filial")
                .brandId(brand.getId())
                .build();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        brandRepository.deleteAll();

        this.brandEntity = brandRepository.save(new BrandEntity(1l, "Ford"));

        Model modelToSave = getModel(this.brandEntity);
        this.model = service.save(modelToSave);
        log.info("Model saved: {}", this.model);
    }

    @Disabled
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/models/{id}", this.model.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Seven Food - Filial"));
    }

    @Disabled
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/models")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }


    @Disabled
    void create() throws Exception {
        String create = JsonUtil.getJson(this.model);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/models")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Disabled
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new Model());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/models")
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
        ModelRequest restaurantRequest = new ModelRequest();
        String create = JsonUtil.getJson(restaurantRequest);

        when(productApiMapper.fromRequest(restaurantRequest)).thenThrow(new RuntimeException("Modele não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/models")
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
        Model savedModel = service.save(getModelUpdate(this.brandEntity));
        Long id = savedModel.getId();
        String update = JsonUtil.getJson(savedModel);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/models/{id}", id)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Seven Food - Filial"));
    }

    @Disabled
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new Model());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/models/{id}", 99L)
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
        ModelRequest product = new ModelRequest();
        String create = JsonUtil.getJson(product);

        when(productApiMapper.fromRequest(product)).thenThrow(new RuntimeException("Modele não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/models/{id}", 99L)
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void deleteModelAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/models/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Disabled
    void findById_productIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/models/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        ModelRequest restaurantRequest = new ModelRequest();
        when(productApiMapper.fromRequest(restaurantRequest)).thenThrow(new RuntimeException("Modele não encontrado ao buscar por id"));

        MvcResult result = mockMvc.perform(get("/v1/models/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}