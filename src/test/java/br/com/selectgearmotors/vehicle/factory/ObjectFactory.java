package br.com.selectgearmotors.vehicle.factory;

import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.domain.VehicleCategory;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehiclecategory.VehicleCategoryEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class ObjectFactory {
    public static ObjectFactory instance;

    private ObjectFactory() {}

    public static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public BrandEntity getRestaurantEntity() {
        return BrandEntity.builder()
                .name("Seven Food")
                .build();
    }

    public Brand getRestaurant() {
        return Brand.builder()
                .name("Seven Food")
                .build();
    }

    public VehicleEntity getVehicleEntity(BrandEntity brandEntity, ModelEntity modelEntity, VehicleCategoryEntity vehicleCategoryEntity) {
        return VehicleEntity.builder()
                .cor("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryEntity(vehicleCategoryEntity)
                .modelEntity(modelEntity)
                .build();
    }

    public VehicleEntity getVehicleEntity1(BrandEntity brandEntity, ModelEntity modelEntity, VehicleCategoryEntity vehicleCategoryEntity) {
        return VehicleEntity.builder()
                .cor("Bebida 1")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryEntity(vehicleCategoryEntity)
                .modelEntity(modelEntity)
                .build();
    }

    public VehicleEntity getVehicleEntity2(BrandEntity brandEntity, ModelEntity modelEntity, VehicleCategoryEntity vehicleCategoryEntity) {
        return VehicleEntity.builder()
                .cor("Bebida 2")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryEntity(vehicleCategoryEntity)
                .modelEntity(modelEntity)
                .build();
    }

    public Vehicle getVehicle(Brand brand, Model model, VehicleCategory vehicleCategory) {
        return Vehicle.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryId(vehicleCategory.getId())
                .modelId(model.getId())
                .build();
    }

    public Vehicle getVehicle1(Model model, VehicleCategory vehicleCategory) {
        return Vehicle.builder()
                .cor("Bebida 1")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryId(vehicleCategory.getId())
                .modelId(model.getId())
                .build();
    }

    public Vehicle getVehicle2(Model model, VehicleCategory vehicleCategory) {
        return Vehicle.builder()
                .cor("Bebida 2")
                .code(UUID.randomUUID().toString())
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleCategoryId(vehicleCategory.getId())
                .modelId(model.getId())
                .build();
    }


    public VehicleCategoryEntity getVehicleTypeEntity() {
        return VehicleCategoryEntity.builder()
                .name("Bebida")
                .build();
    }

    public VehicleCategory getVehicleType() {
        return VehicleCategory.builder()
                .name("Bebida")
                .build();
    }

    public VehicleCategory getVehicleTypeTo260() {
        return VehicleCategory.builder()
                .name("a".repeat(260))
                .build();
    }
}