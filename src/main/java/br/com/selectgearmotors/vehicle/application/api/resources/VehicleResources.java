package br.com.selectgearmotors.vehicle.application.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.VehicleRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.VehicleResponse;
import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.api.mapper.VehicleApiMapper;
import br.com.selectgearmotors.vehicle.commons.Constants;
import br.com.selectgearmotors.vehicle.commons.util.RestUtils;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.ports.in.vehicle.*;
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
@RequestMapping("/v1/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class VehicleResources {

    private final CreateVehiclePort createVehiclePort;
    private final DeleteVehiclePort deleteVehiclePort;
    private final FindByIdVehiclePort findByIdVehiclePort;
    private final FindVehiclesPort findVehiclesPort;
    private final UpdateVehiclePort updateVehiclePort;
    private final VehicleApiMapper productApiMapper;

    @Operation(summary = "Create a new Vehicle", tags = {"products", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = VehicleResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleResponse> save(@Valid @RequestBody VehicleRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            Vehicle product = productApiMapper.fromRequest(request);
            Vehicle saved = createVehiclePort.save(product);
            if (saved == null) {
                throw new ResourceFoundException("Produto não encontroado ao cadastrar");
            }

            VehicleResponse productResponse = productApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(productResponse.getId());

            return ResponseEntity.created(location).body(productResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a Vehicle by Id", tags = {"products", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VehicleResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleResponse> update(@PathVariable("id") Long id, @Valid @RequestBody VehicleRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var product = productApiMapper.fromRequest(request);
            Vehicle updated = updateVehiclePort.update(id, product);
            if (updated == null) {
                throw new ResourceFoundException("Produto não encontroado ao atualizar");
            }

            VehicleResponse productResponse = productApiMapper.fromEntity(updated);
            return ResponseEntity.ok(productResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all Vehicle", tags = {"products", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VehicleResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VehicleResponse>> findAll() {
        List<Vehicle> productList = findVehiclesPort.findAll();
        List<VehicleResponse> productResponse = productApiMapper.map(productList);
        return productResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(productResponse);
    }

    @Operation(
            summary = "Retrieve a Vehicle by Id",
            description = "Get a Vehicle object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"products", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = VehicleResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VehicleResponse> findOne(@PathVariable("id") Long id) {
        try {
            Vehicle productSaved = findByIdVehiclePort.findById(id);
            if (productSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por id");
            }

            VehicleResponse productResponse = productApiMapper.fromEntity(productSaved);
            return ResponseEntity.ok(productResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Retrieve a Vehicle by Id",
            description = "Get a Vehicle object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"products", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = VehicleResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VehicleResponse> findByCode(@PathVariable("code") String code) {
        try {

            Vehicle productSaved = findByIdVehiclePort.findByCode(code);
            if (productSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            VehicleResponse productResponse = productApiMapper.fromEntity(productSaved);
            return ResponseEntity.ok(productResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findByCode: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a Vehicle by Id", tags = {"producttrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteVehiclePort.remove(id);
        return ResponseEntity.noContent().build();
    }
}