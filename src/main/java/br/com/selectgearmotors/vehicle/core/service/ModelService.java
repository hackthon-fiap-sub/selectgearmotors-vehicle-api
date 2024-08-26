package br.com.selectgearmotors.vehicle.core.service;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.core.domain.Model;
import br.com.selectgearmotors.vehicle.core.ports.in.model.*;
import br.com.selectgearmotors.vehicle.core.ports.out.ModelRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModelService implements CreateModelPort, UpdateModelPort, FindByIdModelPort, FindModelsPort, DeleteModelPort {

    private final ModelRepositoryPort modelRepository;

    @Override
    public Model save(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public Model update(Long id, Model model) {
        Model resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, model);

            return modelRepository.save(resultById);
        }

        return null;
    }

    @Override
    public Model findById(Long id) {
        return modelRepository.findById(id);
    }

    @Override
    public List<Model> findAll() {
       return modelRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Model modelById = findById(id);
            if (modelById == null) {
                throw new ResourceFoundException("Model not found");
            }

            modelRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover modele: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
