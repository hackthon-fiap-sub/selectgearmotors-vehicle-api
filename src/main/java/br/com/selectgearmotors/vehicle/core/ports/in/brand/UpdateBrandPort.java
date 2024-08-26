package br.com.selectgearmotors.vehicle.core.ports.in.brand;

import br.com.selectgearmotors.vehicle.core.domain.Brand;

public interface UpdateBrandPort {
    Brand update(Long id, Brand brand);
}
