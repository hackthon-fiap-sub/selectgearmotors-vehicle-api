package br.com.selectgearmotors.vehicle.application.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleCategoryRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleCategoryResponse;
import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleCategoryApiMapper;
import br.com.selectgearmotors.vehicle.commons.Constants;
import br.com.selectgearmotors.vehicle.commons.util.RestUtils;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.ports.in.vehiclecategory.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/vehicle-categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "Content-Category, Authorization", maxAge = 3600)
public class VehicleCategoryResources {

    private final CreateVehicleCategoryPort createVehicleCategoryPort;
    private final DeleteVehicleCategoryPort deleteVehicleCategoryPort;
    private final FindByIdVehicleCategoryPort findByIdVehicleCategoryPort;
    private final FindVehicleCategoriesPort findVehicleCategorysPort;
    private final UpdateVehicleCategoryPort updateVehicleCategoryPort;
    private final VehicleCategoryApiMapper vehicleCategoryApiMapper;

    @Operation(summary = "Create a new VehicleCategory", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = VehicleCategoryResources.class), mediaType= "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleCategoryResponse> save(@Valid @RequestBody VehicleCategoryRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            VehicleCategory vehicleCategory = vehicleCategoryApiMapper.fromRequest(request);
            VehicleCategory saved = createVehicleCategoryPort.save(vehicleCategory);
            if (saved == null) {
                throw new ResourceFoundException("Produto não encontroado ao cadastrar");
            }

            VehicleCategoryResponse productCategoryResponse = vehicleCategoryApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(productCategoryResponse.getId());
            return ResponseEntity.created(location).body(productCategoryResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a VehicleCategory by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VehicleCategoryResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleCategoryResponse> update(@PathVariable("id") Long id, @Valid @RequestBody VehicleCategoryRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = vehicleCategoryApiMapper.fromRequest(request);
            VehicleCategory updated = updateVehicleCategoryPort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            VehicleCategoryResponse productCategoryResponse = vehicleCategoryApiMapper.fromEntity(updated);
            return ResponseEntity.ok(productCategoryResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all VehicleCategory", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VehicleCategoryResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VehicleCategoryResponse>> findAll() {
        List<VehicleCategory> vehicleCategoryList = findVehicleCategorysPort.findAll();
        List<VehicleCategoryResponse> productCategoryResponse = vehicleCategoryApiMapper.map(vehicleCategoryList);
        return productCategoryResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(productCategoryResponse);
    }

    @Operation(
            summary = "Retrieve a VehicleCategory by Id",
            description = "Get a VehicleCategory object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = VehicleCategoryResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VehicleCategoryResponse> findOne(@PathVariable("id") Long id) {
        try {
            VehicleCategory vehicleCategorySaved = findByIdVehicleCategoryPort.findById(id);
            if (vehicleCategorySaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            VehicleCategoryResponse productCategoryResponse = vehicleCategoryApiMapper.fromEntity(vehicleCategorySaved);
            return ResponseEntity.ok(productCategoryResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a VehicleCategory by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteVehicleCategoryPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}