package br.com.selectgearmotors.vehicle.core.ports.in.model;

import br.com.selectgearmotors.vehicle.core.domain.Model;

public interface CreateModelPort {
    Model save(Model model);
}
