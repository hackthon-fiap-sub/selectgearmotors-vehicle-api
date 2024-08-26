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
@Schema(description = "Brand", requiredProperties = {"id, name"})
@Tag(name = "Brand", description = "Brand")
public class Brand implements Serializable {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private String name;

    public void update(Long id, Brand brand) {
        this.id = id;
        this.name = brand.getName();
    }
}
