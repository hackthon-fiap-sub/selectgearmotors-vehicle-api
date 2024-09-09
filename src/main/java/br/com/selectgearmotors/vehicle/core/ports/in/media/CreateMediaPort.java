package br.com.selectgearmotors.vehicle.core.ports.in.media;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.core.domain.Media;

public interface CreateMediaPort {
    Media save(Media client) throws ResourceFoundException;
}
