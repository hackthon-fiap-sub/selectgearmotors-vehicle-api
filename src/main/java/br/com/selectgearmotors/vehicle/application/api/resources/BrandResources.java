package br.com.selectgearmotors.vehicle.application.api.resources;

import br.com.selectgearmotors.vehicle.application.api.dto.request.BrandRequest;
import br.com.selectgearmotors.vehicle.application.api.dto.response.BrandResponse;
import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.api.mapper.BrandApiMapper;
import br.com.selectgearmotors.vehicle.commons.util.RestUtils;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.ports.in.brand.*;
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
@RequestMapping("/v1/brands")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class BrandResources {

    private final CreateBrandPort createBrandPort;
    private final DeleteBrandPort deleteBrandPort;
    private final FindByIdBrandPort findByIdBrandPort;
    private final FindBrandsPort findBrandsPort;
    private final UpdateBrandPort updateBrandPort;
    private final BrandApiMapper brandApiMapper;

    @Operation(summary = "Create a new Brand", tags = {"brands", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = BrandResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BrandResponse> save(@Valid @RequestBody BrandRequest request) {
        log.info("Chegada do objeto para ser salvo {}", request);
        Brand brand = brandApiMapper.fromRequest(request);
        Brand saved = createBrandPort.save(brand);
        if (saved == null) {
            throw new ResourceFoundException("Produto não encontroado ao cadastrar");
        }

        BrandResponse brandResponse = brandApiMapper.fromEntity(saved);
        URI location = RestUtils.getUri(brandResponse.getId());
        return ResponseEntity.created(location).body(brandResponse);
    }

    @Operation(summary = "Update a Brand by Id", tags = {"brands", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BrandResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BrandResponse> update(@PathVariable("id") Long id, @Valid @RequestBody BrandRequest request) {
        log.info("Chegada do objeto para ser alterado {}", request);
        var brand = brandApiMapper.fromRequest(request);
        Brand updated = updateBrandPort.update(id, brand);
        if (updated == null) {
            throw new ResourceFoundException("Brande não encontroado ao atualizar");
        }

        BrandResponse brandResponse = brandApiMapper.fromEntity(updated);
        return ResponseEntity.ok(brandResponse);
    }

    @Operation(summary = "Retrieve all Brand", tags = {"brands", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BrandResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BrandResponse>> findAll() {
        List<Brand> brandList = findBrandsPort.findAll();
        List<BrandResponse> brandResponse = brandApiMapper.map(brandList);
        return brandResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(brandResponse);
    }

    @Operation(
            summary = "Retrieve a Brand by Id",
            description = "Get a Brand object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"brands", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BrandResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BrandResponse> findOne(@PathVariable("id") Long id) {
        Brand brandSaved = findByIdBrandPort.findById(id);
        if (brandSaved == null) {
            throw new ResourceFoundException("Produto não encontrado ao buscar por código");
        }

        BrandResponse brandResponse = brandApiMapper.fromEntity(brandSaved);
        return ResponseEntity.ok(brandResponse);
    }

    @Operation(summary = "Delete a Brand by Id", tags = {"brandtrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteBrandPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}