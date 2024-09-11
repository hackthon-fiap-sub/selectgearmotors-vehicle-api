package br.com.selectgearmotors.vehicle.application.api.dto.request;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleFuel;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Schema(description = "VehicleRequest", requiredProperties = {"cor", "pic", "year", "description", "price", "vehicleTypeId", "brandId", "modelId", "vehicleStatus"})
@Tag(name = "VehicleRequest", description = "Model")
public class VehicleRequest implements Serializable {

    @Schema(description = "name of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    private String cor;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private Long mediaId;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private int vehicleYear;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private int km;

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

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "RestaurantEntity")
    private Long modelId;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    private String vehicleStatus;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    private String vehicleType;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    private String vehicleFuel;

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
