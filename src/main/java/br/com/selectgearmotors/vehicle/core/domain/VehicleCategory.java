package br.com.selectgearmotors.vehicle.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Vehicle Type", requiredProperties = {"id, name"})
@Tag(name = "Vehicle Type", description = "Vehicle Type")
public class VehicleCategory implements Serializable {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private String name;

    public void update(Long id, VehicleCategory vehicleCategory) {
        this.id = id;
        this.name = vehicleCategory.getName();
    }
}
