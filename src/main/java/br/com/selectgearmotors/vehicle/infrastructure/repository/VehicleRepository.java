package br.com.selectgearmotors.vehicle.infrastructure.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.domain.VehicleStatus;
import br.com.selectgearmotors.vehicle.infrastructure.entity.vehicle.VehicleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    VehicleEntity findByCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE VehicleEntity v SET v.vehicleStatus = :status WHERE v.code = :code")
    void updateStatus(@Param("status") VehicleStatus status, @Param("code") String code);
}
