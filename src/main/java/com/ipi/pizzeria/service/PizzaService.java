package com.ipi.pizzeria.service;

import com.ipi.pizzeria.dto.*;
import com.ipi.pizzeria.entity.IngredientEntity;
import com.ipi.pizzeria.entity.PizzaEntity;
import com.ipi.pizzeria.repository.IngredientRepository;
import com.ipi.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService implements IPizzaService {

    // Taux de TVA restauration

    private static final double TAUX_TVA = 1.055;

    @Autowired
    private PizzaRepository repository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IIngredientService ingredientService;


    // arrondir à 2 décimales

    private Double arrondir(Double valeur) {
        return Math.round(valeur * 100.0) / 100.0;
    }

    //  CRUD

    @Override
    public PizzaDto toDto(PizzaEntity entity) {
        PizzaDto dto = new PizzaDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrixVente(entity.getPrixVente());


        List<IngredientDto> ingredients = new ArrayList<>();
        for (IngredientEntity ing : entity.getIngredients()) {
            ingredients.add(ingredientService.toDto(ing));
        }
        dto.setIngredients(ingredients);
        return dto;
    }

    @Override
    public List<PizzaDto> getAll() {
        List<PizzaEntity> list = repository.findAll();
        List<PizzaDto> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(toDto(list.get(i)));
        }
        return result;
    }

    @Override
    public PizzaDto get(Long id) {
        PizzaEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return toDto(entity);
    }

    @Override
    public Boolean estValide(PizzaAddDto dto) {
        if (dto.getNom() == null || dto.getNom().isBlank()) {
            return false;
        }
        if (dto.getPrixVente() == null || dto.getPrixVente() <= 0) {
            return false;
        }
        if (dto.getIngredientIds() == null || dto.getIngredientIds().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean ingredientsExistent(List<Long> ingredientIds) {
        for (Long id : ingredientIds) {
            if (!ingredientRepository.existsById(id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PizzaDto ajouter(PizzaAddDto dto) {
        PizzaEntity entity = new PizzaEntity();
        entity.setNom(dto.getNom());
        entity.setPrixVente(dto.getPrixVente());

        //ingrédient en base grâce à son id
        List<IngredientEntity> ingredients = new ArrayList<>();
        for (Long id : dto.getIngredientIds()) {
            ingredients.add(ingredientRepository.findById(id).get());
        }
        entity.setIngredients(ingredients);

        // Insert dans pizza et aussi Insert dans pizza_ingredient
        repository.saveAndFlush(entity);
        return toDto(entity);
    }

    @Override
    public Boolean exist(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void supprimer(Long id) {
        repository.deleteById(id);
    }


    //  calucul metier  sans arrondi

    @Override
    public Double calculerPrixHT(Double prixTTC) {
        return prixTTC / TAUX_TVA;

    }

    @Override
    public Double calculerTva(Double prixTTC) {
        return prixTTC - calculerPrixHT(prixTTC);

    }

    @Override
    public Double calculerCoutIngredients(PizzaEntity pizza) {
        double total = 0;
        for (IngredientEntity ing : pizza.getIngredients()) {
            total = total + ing.getPrixCoutant();
        }
        return total;
    }

    @Override
    public Double calculerMargeBrute(PizzaEntity pizza) {
        Double prixHT = calculerPrixHT(pizza.getPrixVente());
        Double cout = calculerCoutIngredients(pizza);
        return prixHT - cout;
    }

    // arrondit

    @Override
    public PizzaFinancierDto getFinancier(Long id) {
        PizzaEntity pizza = repository.findById(id).orElse(null);
        if (pizza == null) {
            return null;
        }

        PizzaFinancierDto dto = new PizzaFinancierDto();
        dto.setPizzaId(pizza.getId());
        dto.setNomPizza(pizza.getNom());
        dto.setPrixVenteTTC(arrondir(pizza.getPrixVente()));
        dto.setPrixVenteHT(arrondir(calculerPrixHT(pizza.getPrixVente())));
        dto.setTva5_5(arrondir(calculerTva(pizza.getPrixVente())));
        dto.setCoutTotalIngredients(arrondir(calculerCoutIngredients(pizza)));
        dto.setMargeBrute(arrondir(calculerMargeBrute(pizza)));
        return dto;
    }

    @Override
    public StatistiquesDto getStatistiques() {
        List<PizzaEntity> pizzas = repository.findAll();
        StatistiquesDto dto = new StatistiquesDto();
        dto.setNbPizzas(pizzas.size());

        if (pizzas.isEmpty()) {
            dto.setPrixMoyenVenteTTC(0.0);
            return dto;
        }

        double sommePrix = 0;
        PizzaEntity plusRentable = pizzas.get(0);
        PizzaEntity moinsRentable = pizzas.get(0);

        for (PizzaEntity p : pizzas) {
            sommePrix = sommePrix + p.getPrixVente();

            // marge plus grande que la meilleure actuelle ? = nouvelle meilleure
            if (calculerMargeBrute(p) > calculerMargeBrute(plusRentable)) {
                plusRentable = p;
            }
            // marge plus petite que la pire actuelle ? = nouvelle pire
            if (calculerMargeBrute(p) < calculerMargeBrute(moinsRentable)) {
                moinsRentable = p;
            }
        }

        dto.setPrixMoyenVenteTTC(arrondir(sommePrix / pizzas.size()));
        dto.setPizzaLaPlusRentable(plusRentable.getNom());
        dto.setPizzaLaMoinsRentable(moinsRentable.getNom());
        return dto;
    }
}