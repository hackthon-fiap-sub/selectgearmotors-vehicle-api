package br.com.selectgearmotors.vehicle.core.ports.in.media;

import br.com.selectgearmotors.vehicle.core.domain.Media;

public interface FindByIdMediaPort {
    Media findById(Long id);
}
