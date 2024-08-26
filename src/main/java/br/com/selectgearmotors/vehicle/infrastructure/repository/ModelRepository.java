package br.com.selectgearmotors.vehicle.infrastructure.repository;

import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    Optional<ModelEntity> findByName(String name);
}
