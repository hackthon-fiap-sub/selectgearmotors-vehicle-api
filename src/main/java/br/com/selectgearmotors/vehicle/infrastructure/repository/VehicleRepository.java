package br.com.selectgearmotors.vehicle.infrastructure.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    VehicleEntity findByCode(String code);
}
