package br.com.selectgearmotors.vehicle.application.database.repository;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.commons.exception.CNPJFoundException;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.application.database.mapper.BrandMapper;
import br.com.selectgearmotors.vehicle.core.ports.out.BrandRepositoryPort;
import br.com.selectgearmotors.vehicle.infrastructure.entity.brand.BrandEntity;
import br.com.selectgearmotors.vehicle.infrastructure.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BrandRepositoryAdapter implements BrandRepositoryPort {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Brand save(Brand brand) {
        BrandEntity brandEntity = brandMapper.fromModelToEntity(brand);

        BrandEntity saved = brandRepository.save(brandEntity);
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
    public Brand findById(Long id) {
        Optional<BrandEntity> buBrand = brandRepository.findById(id);
        if (buBrand.isPresent()) {
            return brandMapper.fromEntityToModel(buBrand.get());
        }
        return null;
    }

    @Override
    public List<Brand> findAll() {
        List<BrandEntity> all = brandRepository.findAll();
        return brandMapper.map(all);
    }

    @Override
    public Brand update(Long id, Brand brand) {
        Optional<BrandEntity> resultById = brandRepository.findById(id);
        if (resultById.isPresent()) {

            BrandEntity brandToChange = resultById.get();
            brandToChange.update(id, brandToChange);

            return brandMapper.fromEntityToModel(brandRepository.save(brandToChange));
        }

        return null;
    }
}
