package br.com.selectgearmotors.vehicle.core.ports.in.brand;

import br.com.selectgearmotors.vehicle.core.domain.Brand;

import java.util.List;

public interface FindBrandsPort {
    List<Brand> findAll();
}
