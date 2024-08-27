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
@Schema(description = "Model", requiredProperties = {"id, name"})
@Tag(name = "Model", description = "Model")
public class Model implements Serializable {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    private String name;

    private Long brandId;

    public void update(Long id, Model model) {
        this.id = id;
        this.name = model.getName();
        this.brandId = model.getBrandId();
    }
}
