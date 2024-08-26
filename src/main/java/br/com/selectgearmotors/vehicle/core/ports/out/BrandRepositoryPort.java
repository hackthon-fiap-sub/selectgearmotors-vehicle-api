package br.com.selectgearmotors.vehicle.core.ports.out;

import br.com.selectgearmotors.vehicle.core.domain.Brand;

import java.util.List;

public interface BrandRepositoryPort {
    Brand save(Brand brand);
    boolean remove(Long id);
    Brand findById(Long id);
    List<Brand> findAll();
    Brand update(Long id, Brand brand);
}
