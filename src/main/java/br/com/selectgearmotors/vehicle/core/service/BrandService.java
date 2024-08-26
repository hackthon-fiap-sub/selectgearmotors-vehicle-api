package br.com.selectgearmotors.vehicle.core.service;

import br.com.selectgearmotors.vehicle.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehicle.core.domain.Brand;
import br.com.selectgearmotors.vehicle.core.ports.in.brand.*;
import br.com.selectgearmotors.vehicle.core.ports.out.BrandRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BrandService implements CreateBrandPort, UpdateBrandPort, FindByIdBrandPort, FindBrandsPort, DeleteBrandPort {

    private final BrandRepositoryPort brandRepository;

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Long id, Brand brand) {
        Brand resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, brand);

            return brandRepository.save(resultById);
        }

        return null;
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public List<Brand> findAll() {
       return brandRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Brand brandById = findById(id);
            if (brandById == null) {
                throw new ResourceFoundException("Brand not found");
            }

            brandRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover brande: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
