package br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.AuditDomain;
import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
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

import java.math.BigDecimal;

@Entity
@Table(name = "tb_vehicle", schema = "vehicle")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Vehicle Entity", requiredProperties = {"id, code, name, price, productCategory, restaurant"})
@Tag(name = "Vehicle Entity", description = "Model")
public class VehicleEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Product.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "name of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"code\" é obrigario")
    @Size(min = 3, max = 255)
    @Column(name = "code", length = 255)
    private String code;

    @Schema(description = "name of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 1, max = 255)
    @Column(name = "cor", length = 255)
    private String cor;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private String pic;

    @Schema(description = "picture of the Product.",
            example = "V$")
    private int year;

    @Schema(description = "description of the Product.",
            example = "V$")
    @Size(min = 0, max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Schema(description = "price of the Product.",
            example = "V$")
    @NotNull(message = "o campo \"price\" é obrigario")
    private BigDecimal price;

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "ProductCategoryEntity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private VehicleTypeEntity vehicleTypeEntity;

    @Schema(description = "Restaurant of the User.",
            example = "1", ref = "RestaurantEntity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "model_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ModelEntity modelEntity;

    @Schema(description = "Status of the Product.",
            example = "ACTIVE")
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status")
    private VehicleStatus vehicleStatus;

    public void update(Long id, VehicleEntity vehicleEntity) {
        this.id = id;
        this.code = vehicleEntity.getCode();
        this.cor = vehicleEntity.getCor();
        this.pic = vehicleEntity.getPic();
        this.year = vehicleEntity.getYear();
        this.description = vehicleEntity.getDescription();
        this.price = vehicleEntity.getPrice();
        this.vehicleTypeEntity = vehicleEntity.getVehicleTypeEntity();
        this.modelEntity = vehicleEntity.getModelEntity();
        this.vehicleStatus = vehicleEntity.getVehicleStatus();
    }
}
