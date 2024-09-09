package br.com.selectgearmotors.vehicle.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleRequest;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleApiMapper;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.service.ModelService;
import br.com.selectgearmotors.vehicle.core.service.VehicleCategoryService;
import br.com.selectgearmotors.vehicle.core.service.BrandService;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleCategoryRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.VehicleRepository;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import br.com.selectgearmotors.vehicle.util.JsonUtil;
import br.com.selectgearmotors.vehicle.core.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class VehicleResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private VehicleService service;

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private VehicleCategoryService vehicleCategoryService;

    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelRepository modelRepository;

    @Mock
    private VehicleApiMapper vehicleApiMapper;

    private Faker faker = new Faker();
    private Long vehicleCategoryId;
    private Long brandId;
    private Long modelId;
    private Long vehicleId;
    private String vehicleCode;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        vehicleCategoryRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();

        VehicleCategory vehicleCategory = getVehicleCategory();
        vehicleCategory = vehicleCategoryService.save(vehicleCategory);
        this.vehicleCategoryId = vehicleCategory.getId();

        Brand brand = getBrand();
        var brandSaved = brandService.save(brand);
        this.brandId = brandSaved.getId();

        Model model = getModel(this.brandId);
        var modelSaved = modelService.save(model);
        this.modelId = modelSaved.getId();

        Vehicle vehicleFounded = getVehicle(vehicleCategoryId, modelId);
        var vehicleSaved = service.save(vehicleFounded);
        this.vehicleId = vehicleSaved.getId();
        this.vehicleCode = vehicleSaved.getCode();// Save the vehicle ID for use in tests

        verifyDataSaved(vehicleCategory, modelSaved, brandSaved, vehicleSaved);
    }

    private Model getModel(Long brandId) {
        return Model.builder()
                .name(faker.company().name())
                .brandId(brandId)
                .build();
    }

    private void verifyDataSaved(VehicleCategory vehicleCategory, Model model, Brand brand, Vehicle vehicle) {
        assertThat(vehicleCategoryRepository.findById(vehicleCategory.getId())).isPresent();
        assertThat(modelRepository.findById(model.getId())).isPresent();
        assertThat(brandRepository.findById(brand.getId())).isPresent();
        assertThat(repository.findById(vehicle.getId())).isPresent();
    }

    private VehicleCategory getVehicleCategory() {
        return VehicleCategory.builder()
                .name(faker.commerce().department())
                .build();
    }

    private Brand getBrand() {
        return Brand.builder()
                .name(faker.company().name())
                .build();
    }

    private Vehicle getVehicle(Long vehicleCategoryId, Long modelId) {
        return Vehicle.builder()
                .cor(faker.commerce().color())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
                .description(faker.lorem().sentence())
                .vehicleCategoryId(vehicleCategoryId)
                .modelId(modelId)
                .vehicleStatus("AVAILABLE")
                .build();
    }

    private VehicleEntity getVehicleEntity(Long vehicleCategoryId, Long brandId, Long modelId) {
        Optional<VehicleCategoryEntity> vehicleCategoryById = vehicleCategoryRepository.findById(vehicleCategoryId);
        Optional<ModelEntity> modelById = modelRepository.findById(brandId);

        return VehicleEntity.builder()
                .cor(faker.commerce().color())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
                .description(faker.lorem().sentence())
                .vehicleCategoryEntity(vehicleCategoryById.isPresent() ? vehicleCategoryById.get() : null)
                .modelEntity(modelById.isPresent() ? modelById.get() : null)
                .build();
    }

    private Vehicle getVehicleUpdate(Long vehicleCategoryId, Long modelId) {
        return Vehicle.builder()
                .id(vehicleId) // Ensure we are updating the same vehicle
                .cor(faker.commerce().color())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
                .description(faker.lorem().sentence())
                .vehicleCategoryId(vehicleCategoryId)
                .modelId(modelId)
                .build();
    }

    @Disabled
    void findsTaskById() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/vehicles/{id}", this.vehicleId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/vehicles")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void getAll_isNull() throws Exception {
        repository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/vehicles")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        repository.deleteAll();
        vehicleCategoryRepository.findById(this.vehicleCategoryId).ifPresent(vehicleCategory -> {
            assertThat(vehicleCategory).isNotNull();
            this.vehicleCategoryId = vehicleCategory.getId();
        });

        brandRepository.findById(this.brandId).ifPresent(brand -> {
            assertThat(brand).isNotNull();
            this.brandId = brand.getId();
        });

        Vehicle vehicle = getVehicle(this.vehicleCategoryId, this.brandId);
        String create = JsonUtil.getJson(vehicle);

        assertThat(create).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/vehicles")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new Vehicle());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/vehicles")
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
        Vehicle vehicleUpdate = getVehicleUpdate(vehicleCategoryId, modelId);
        String update = JsonUtil.getJson(vehicleUpdate);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/vehicles/{id}", vehicleId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/vehicles/{id}", vehicleId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").exists());
    }

    @Disabled
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new Vehicle());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/vehicles/{id}", this.vehicleId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/vehicles/{id}", this.vehicleId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void findByCode_vehicleFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/vehicles/code/{code}", this.vehicleCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void testByCode_Exception() throws Exception {
        VehicleRequest vehicle = new VehicleRequest();
        when(vehicleApiMapper.fromRequest(vehicle)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por código"));

        MvcResult result = mockMvc.perform(get("/v1/vehicles/code/{code}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void findByCode_vehicleIsNull() throws Exception {
        String vehicleCode = UUID.randomUUID().toString();
        MvcResult result = mockMvc.perform(get("/v1/vehicles/code/{code}", vehicleCode))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        VehicleRequest vehicle = new VehicleRequest();
        when(vehicleApiMapper.fromRequest(vehicle)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por id"));

        MvcResult result = mockMvc.perform(get("/v1/vehicles/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}