package com.ipi.pizzeria.service;

import com.ipi.pizzeria.dto.IngredientAddDto;
import com.ipi.pizzeria.dto.IngredientDto;
import com.ipi.pizzeria.entity.IngredientEntity;
import com.ipi.pizzeria.repository.IngredientRepository;
import com.ipi.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService implements IIngredientService {

    @Autowired
    private IngredientRepository repository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public IngredientDto toDto(IngredientEntity entity) {
        IngredientDto dto = new IngredientDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrixCoutant(entity.getPrixCoutant());
        return dto;
    }

    @Override
    public List<IngredientDto> getAll() {
        List<IngredientEntity> list = repository.findAll();
        List<IngredientDto> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(toDto(list.get(i)));
        }
        return result;
    }

    @Override
    public IngredientDto get(Long id) {
        IngredientEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return toDto(entity);
    }

    @Override
    public Boolean estValide(IngredientAddDto dto) {
        if (dto.getNom() == null || dto.getNom().isBlank()) {
        }
        if (dto.getPrixCoutant() == null || dto.getPrixCoutant() < 0) {
            return false;
        }
        return true;
    }

    @Override
    public IngredientDto ajouter(IngredientAddDto dto) {
        // on convertit le DTO d'entrée en Entity
        IngredientEntity entity = new IngredientEntity();
        entity.setNom(dto.getNom());
        entity.setPrixCoutant(dto.getPrixCoutant());

        repository.saveAndFlush(entity);
        return toDto(entity);
    }

    @Override
    public Boolean exist(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Boolean estUtilise(Long id) {
        return pizzaRepository.existsByIngredients_Id(id);
    }

    @Override
    public void supprimer(Long id) {
        repository.deleteById(id);
    }
}