package br.com.selectgearmotors.vehicle.factory;

import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.domain.Vehicle;
import br.com.selectgearmotors.vehicle.core.domain.VehicleType;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;

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

    public VehicleEntity getVehicleEntity(BrandEntity brandEntity, ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .brandEntity(brandEntity)
                .modelEntity(modelEntity)
                .build();
    }

    public VehicleEntity getVehicleEntity1(BrandEntity brandEntity, ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida 1")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .brandEntity(brandEntity)
                .modelEntity(modelEntity)
                .build();
    }

    public VehicleEntity getVehicleEntity2(BrandEntity brandEntity, ModelEntity modelEntity, VehicleTypeEntity vehicleTypeEntity) {
        return VehicleEntity.builder()
                .cor("Bebida 2")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeEntity(vehicleTypeEntity)
                .brandEntity(brandEntity)
                .modelEntity(modelEntity)
                .build();
    }

    public Vehicle getVehicle(Brand brand, Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .brandId(brand.getId())
                .modelId(model.getId())
                .build();
    }

    public Vehicle getVehicle1(Brand brand, Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Bebida 1")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .brandId(brand.getId())
                .modelId(model.getId())
                .build();
    }

    public Vehicle getVehicle2(Brand brand, Model model, VehicleType vehicleType) {
        return Vehicle.builder()
                .cor("Bebida 2")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .price(BigDecimal.TEN)
                .description("Coca-Cola")
                .vehicleTypeId(vehicleType.getId())
                .brandId(brand.getId())
                .modelId(model.getId())
                .build();
    }


    public VehicleTypeEntity getVehicleTypeEntity() {
        return VehicleTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    public VehicleType getVehicleType() {
        return VehicleType.builder()
                .name("Bebida")
                .build();
    }

    public VehicleType getVehicleTypeTo260() {
        return VehicleType.builder()
                .name("a".repeat(260))
                .build();
    }
}