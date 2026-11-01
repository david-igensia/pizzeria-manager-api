package com.ipi.pizzeria.service;

import com.ipi.pizzeria.dto.IngredientAddDto;
import com.ipi.pizzeria.dto.IngredientDto;
import com.ipi.pizzeria.entity.IngredientEntity;

import java.util.List;

public interface IIngredientService {

/**   Trasformer une entite JPA en DTO sortie*/

    IngredientDto toDto(IngredientEntity entity);

/** recupere touts les ingredients et les transformer en DTO*/

    List<IngredientDto> getAll();

/** Recupere un ingredient par son id.*/

    IngredientDto get(Long id);

/** Verifier que les donnes reçus sont correctes, le nom pas vide, prix positif.*/

    Boolean estValide(IngredientAddDto dto);

/**enregistre un nouvel ingredient renvoyer le DTO*/

    IngredientDto ajouter(IngredientAddDto dto);

/** Reenvoyer vrai si l'id exite dans la table ingredient */

    Boolean exist(Long id);

/** renvoyer vrai si l'ingredient est utilisé dans au moins une pizza*/

    Boolean estUtilise(Long id);

/** Supprimer l'ingredient */

    void supprimer(Long id);
}
