package br.com.selectgearmotors.vehicle.infrastructure.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);
}
