package br.com.selectgearmotors.vehicle.application.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.ModelRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.ModelResponse;
import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.api.mapper.ModelApiMapper;
import br.com.selectgearmotors.vehicle.commons.util.RestUtils;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.ports.in.brand.*;
import br.com.selectgearmotors.vehicle.core.ports.in.model.*;
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
@RequestMapping("/v1/models")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class ModelResources {

    private final CreateModelPort createModelPort;
    private final DeleteModelPort deleteModelPort;
    private final FindByIdModelPort findByIdModelPort;
    private final FindModelsPort findModelsPort;
    private final UpdateModelPort updateModelPort;
    private final ModelApiMapper brandApiMapper;

    @Operation(summary = "Create a new Model", tags = {"brands", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ModelResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModelResponse> save(@Valid @RequestBody ModelRequest request) {
        log.info("Chegada do objeto para ser salvo {}", request);
        Model brand = brandApiMapper.fromRequest(request);
        Model saved = createModelPort.save(brand);
        if (saved == null) {
            throw new ResourceFoundException("Produto não encontroado ao cadastrar");
        }

        ModelResponse brandResponse = brandApiMapper.fromEntity(saved);
        URI location = RestUtils.getUri(brandResponse.getId());
        return ResponseEntity.created(location).body(brandResponse);
    }

    @Operation(summary = "Update a Model by Id", tags = {"brands", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ModelResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ModelResponse> update(@PathVariable("id") Long id, @Valid @RequestBody ModelRequest request) {
        log.info("Chegada do objeto para ser alterado {}", request);
        var brand = brandApiMapper.fromRequest(request);
        Model updated = updateModelPort.update(id, brand);
        if (updated == null) {
            throw new ResourceFoundException("Modele não encontroado ao atualizar");
        }

        ModelResponse brandResponse = brandApiMapper.fromEntity(updated);
        return ResponseEntity.ok(brandResponse);
    }

    @Operation(summary = "Retrieve all Model", tags = {"brands", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ModelResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ModelResponse>> findAll() {
        List<Model> brandList = findModelsPort.findAll();
        List<ModelResponse> brandResponse = brandApiMapper.map(brandList);
        return brandResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(brandResponse);
    }

    @Operation(
            summary = "Retrieve a Model by Id",
            description = "Get a Model object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"brands", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ModelResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ModelResponse> findOne(@PathVariable("id") Long id) {
        Model brandSaved = findByIdModelPort.findById(id);
        if (brandSaved == null) {
            throw new ResourceFoundException("Produto não encontrado ao buscar por código");
        }

        ModelResponse brandResponse = brandApiMapper.fromEntity(brandSaved);
        return ResponseEntity.ok(brandResponse);
    }

    @Operation(summary = "Delete a Model by Id", tags = {"brandtrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteModelPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}