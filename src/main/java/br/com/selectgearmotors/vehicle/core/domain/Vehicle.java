package br.com.selectgearmotors.vehicle.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Schema(description = "name of the Product.",
            example = "V$")
    private String pic;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long vehicleTypeId;

    private String vehicleTypeName;

    private Long brandId;

    private String brandName;

    @Schema(description = "name of the Product.",
            example = "V$")
    private Long modelId;

    private String modelName;

    private String vehicleStatus;

    public void update(Long id, Vehicle vehicle) {
        this.id = id;
        this.code = vehicle.getCode();
        this.cor = vehicle.getCor();
        this.vehicleYear = vehicle.getVehicleYear();
        this.description = vehicle.getDescription();
        this.price = vehicle.getPrice();
        this.pic = vehicle.getPic();
        this.vehicleTypeId = vehicle.getVehicleTypeId();
        this.modelId = vehicle.getModelId();
        this.vehicleStatus = vehicle.getVehicleStatus();
    }
}
