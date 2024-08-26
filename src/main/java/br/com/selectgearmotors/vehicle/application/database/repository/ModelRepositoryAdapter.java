package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.application.database.mapper.ModelMapper;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.ports.out.ModelRepositoryPort;
import br.com.selectgearmotors.vehicle.infrastructure.entity.model.ModelEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModelRepositoryAdapter implements ModelRepositoryPort {

    private final ModelRepository brandRepository;
    private final ModelMapper brandMapper;

    @Override
    public Model save(Model brand) {
        ModelEntity brandEntity = brandMapper.fromModelToEntity(brand);

        ModelEntity saved = brandRepository.save(brandEntity);
        if (saved.getName() == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio");
        }
        return brandMapper.fromEntityToModel(saved);
    }

    @Override
    public boolean remove(Long id) {
         try {
            brandRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Model findById(Long id) {
        Optional<ModelEntity> buModel = brandRepository.findById(id);
        if (buModel.isPresent()) {
            return brandMapper.fromEntityToModel(buModel.get());
        }
        return null;
    }

    @Override
    public List<Model> findAll() {
        List<ModelEntity> all = brandRepository.findAll();
        return brandMapper.map(all);
    }

    @Override
    public Model update(Long id, Model brand) {
        Optional<ModelEntity> resultById = brandRepository.findById(id);
        if (resultById.isPresent()) {

            ModelEntity brandToChange = resultById.get();
            brandToChange.update(id, brandToChange);

            return brandMapper.fromEntityToModel(brandRepository.save(brandToChange));
        }

        return null;
    }
}
