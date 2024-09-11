package br.com.selectgearmotors.vehicle.application.api.dto.response;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "VehicleRequest", requiredProperties = {"id", "code", "cor", "pic", "year", "description", "price", "vehicleTypeEntity", "brandEntity", "modelEntity", "vehicleStatus"})
@Tag(name = "VehicleRequest", description = "Model")
public class VehicleResponse implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1")
    private Long id;

    private String code;

    @Schema(description = "name of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "cor", length = 255)
    private String cor;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private Long mediaId;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private int vehicleYear;

    @Schema(description = "description of the Product.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String description;

    @Schema(description = "price of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"price\" é obrigario")
    private BigDecimal price;

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "ProductCategoryEntity")
    @NotNull
    private Long vehicleCategoryId;

    private String vehicleCategoryName;

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "RestaurantEntity")
    @NotNull
    private Long modelId;

    private String modelName;

    private Long brandId;

    private String brandName;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    private VehicleStatus vehicleStatus;

    private String location;

    @Schema(description = "description of the Product.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String plate;

    @Schema(description = "description of the Product.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String chassis;

    @Schema(description = "description of the Product.",
            example = "V$")
    @Size(min = 0, max = 255)
    private String renavam;
}