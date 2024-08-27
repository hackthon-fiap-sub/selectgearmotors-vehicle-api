package br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.AuditDomain;
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
@Table(name = "tb_vehicle_type", schema = "vehicle")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "VehicleTypeEntity", requiredProperties = {"id, name"})
@Tag(name = "VehicleTypeEntity", description = "Model")
public class VehicleTypeEntity extends AuditDomain {

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

    public void update(Long id, VehicleTypeEntity vehicleTypeEntity) {
        this.id = id;
        this.name = vehicleTypeEntity.getName();
    }
}