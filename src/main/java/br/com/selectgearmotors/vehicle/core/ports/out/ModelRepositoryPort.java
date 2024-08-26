package br.com.selectgearmotors.vehicle.core.ports.out;

import br.com.selectgearmotors.vehicle.core.domain.Model;

import java.util.List;

public interface ModelRepositoryPort {
    Model save(Model model);
    boolean remove(Long id);
    Model findById(Long id);
    List<Model> findAll();
    Model update(Long id, Model model);
}
