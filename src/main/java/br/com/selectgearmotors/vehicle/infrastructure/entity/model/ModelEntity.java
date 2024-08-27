package br.com.selectgearmotors.vehicle.infrastructure.entity.model;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.AuditDomain;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_model", schema = "vehicle")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ModelEntity", requiredProperties = {"id, name"})
@Tag(name = "ModelEntity", description = "Model")
public class ModelEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "name of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "RestaurantEntity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BrandEntity brandEntity;

    public void update(Long id, ModelEntity modelEntity) {
        this.id = id;
        this.name = modelEntity.getName();
        this.brandEntity = modelEntity.getBrandEntity();
    }
}
