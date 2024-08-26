package br.com.selectgearmotors.vehicle.application.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleTypeRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleTypeResponse;
import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleTypeApiMapper;
import br.com.selectgearmotors.vehicle.commons.Constants;
import br.com.selectgearmotors.vehicle.commons.util.RestUtils;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.ports.in.vehicletype.*;
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
@RequestMapping("/v1/product-categories")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class VehicleTypeResources {

    private final CreateVehicleTypePort createVehicleTypePort;
    private final DeleteVehicleTypePort deleteVehicleTypePort;
    private final FindByIdVehicleTypePort findByIdVehicleTypePort;
    private final FindVehicleTypesPort findVehicleTypesPort;
    private final UpdateVehicleTypePort updateVehicleTypePort;
    private final VehicleTypeApiMapper vehicleTypeApiMapper;

    @Operation(summary = "Create a new VehicleType", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = VehicleTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleTypeResponse> save(@Valid @RequestBody VehicleTypeRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            VehicleType vehicleType = vehicleTypeApiMapper.fromRequest(request);
            VehicleType saved = createVehicleTypePort.save(vehicleType);
            if (saved == null) {
                throw new ResourceFoundException("Produto não encontroado ao cadastrar");
            }

            VehicleTypeResponse productCategoryResponse = vehicleTypeApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(productCategoryResponse.getId());
            return ResponseEntity.created(location).body(productCategoryResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a VehicleType by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VehicleTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleTypeResponse> update(@PathVariable("id") Long id, @Valid @RequestBody VehicleTypeRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = vehicleTypeApiMapper.fromRequest(request);
            VehicleType updated = updateVehicleTypePort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            VehicleTypeResponse productCategoryResponse = vehicleTypeApiMapper.fromEntity(updated);
            return ResponseEntity.ok(productCategoryResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all VehicleType", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VehicleTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VehicleTypeResponse>> findAll() {
        List<VehicleType> vehicleTypeList = findVehicleTypesPort.findAll();
        List<VehicleTypeResponse> productCategoryResponse = vehicleTypeApiMapper.map(vehicleTypeList);
        return productCategoryResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(productCategoryResponse);
    }

    @Operation(
            summary = "Retrieve a VehicleType by Id",
            description = "Get a VehicleType object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = VehicleTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VehicleTypeResponse> findOne(@PathVariable("id") Long id) {
        try {
            VehicleType vehicleTypeSaved = findByIdVehicleTypePort.findById(id);
            if (vehicleTypeSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            VehicleTypeResponse productCategoryResponse = vehicleTypeApiMapper.fromEntity(vehicleTypeSaved);
            return ResponseEntity.ok(productCategoryResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a VehicleType by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteVehicleTypePort.remove(id);
        return ResponseEntity.noContent().build();
    }
}