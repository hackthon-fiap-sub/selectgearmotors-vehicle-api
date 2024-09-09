package br.com.selectgearmotors.vehicle.core.ports.out;

import br.com.selectgearmotors.vehicle.core.domain.Media;

import java.util.List;

public interface MediaRepositoryPort {
    Media save(Media client);
    boolean remove(Long id);
    Media findById(Long id);
    List<Media> findAll();
    Media update(Long id, Media client);
    Media findByName(String name);
}
