package br.com.selectgearmotors.vehicle.core.ports.in.model;

import br.com.selectgearmotors.vehicle.core.domain.Model;

public interface UpdateModelPort {
    Model update(Long id, Model model);
}
