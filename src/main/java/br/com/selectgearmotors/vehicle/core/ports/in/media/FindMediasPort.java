package br.com.selectgearmotors.vehicle.core.ports.in.media;

import br.com.selectgearmotors.vehicle.core.domain.Media;

import java.util.List;

public interface FindMediasPort {
    List<Media> findAll();
}
