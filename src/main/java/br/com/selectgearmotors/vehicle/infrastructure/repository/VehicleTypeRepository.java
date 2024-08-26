package br.com.selectgearmotors.vehicle.infrastructure.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicletype.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long> {
    Optional<VehicleTypeEntity> findByName(String name);
}
