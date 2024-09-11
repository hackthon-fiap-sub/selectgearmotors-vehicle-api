package br.com.selectgearmotors.vehicle.core.domain;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleFuel;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Schema(description = "Product", requiredProperties = {"id, code, name, price, productCategory, restaurant"})
@Tag(name = "Product", description = "Model")
public class Vehicle implements Serializable {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private Long id;

    @Schema(description = "code of the Product.",
            example = "V$")
    private String code;

    private String cor;

    private int vehicleYear;

    @Schema(description = "name of the Product.",
            example = "V$")
    private String description;

    @Schema(description = "name of the Product.",
            example = "V$")
    private BigDecimal price;

    private int km;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long mediaId;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long vehicleCategoryId;

    private String vehicleCategoryName;

    private Long brandId;

    private String brandName;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long modelId;

    private String modelName;

    private String vehicleStatus;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    private String vehicleType;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    private String vehicleFuel;

    @Schema(description = "description of the Product.",
            example = "V$")
    @Size(min = 0, max = 255)
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

    public void update(Long id, Vehicle vehicle) {
        this.id = id;
        this.code = vehicle.getCode();
        this.cor = vehicle.getCor();
        this.vehicleYear = vehicle.getVehicleYear();
        this.description = vehicle.getDescription();
        this.price = vehicle.getPrice();
        this.mediaId = vehicle.getMediaId();
        this.vehicleCategoryId = vehicle.getVehicleCategoryId();
        this.modelId = vehicle.getModelId();
        this.vehicleStatus = vehicle.getVehicleStatus();
        this.location = vehicle.getLocation();
        this.plate = vehicle.getPlate();
        this.chassis = vehicle.getChassis();
        this.renavam = vehicle.getRenavam();
    }
}
