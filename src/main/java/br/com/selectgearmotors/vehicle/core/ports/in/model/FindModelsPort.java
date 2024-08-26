package br.com.selectgearmotors.vehicle.core.ports.in.model;

import br.com.selectgearmotors.vehicle.core.domain.Model;

import java.util.List;

public interface FindModelsPort {
    List<Model> findAll();
}
