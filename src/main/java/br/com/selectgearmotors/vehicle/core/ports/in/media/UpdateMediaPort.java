package br.com.selectgearmotors.vehicle.core.ports.in.media;

import br.com.selectgearmotors.vehicle.core.domain.Media;

public interface UpdateMediaPort {
    Media update(Long id, Media client);
}
