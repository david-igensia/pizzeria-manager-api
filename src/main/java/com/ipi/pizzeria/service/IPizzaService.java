package com.ipi.pizzeria.service;

import com.ipi.pizzeria.dto.PizzaAddDto;
import com.ipi.pizzeria.dto.PizzaDto;
import com.ipi.pizzeria.dto.PizzaFinancierDto;
import com.ipi.pizzeria.dto.StatistiquesDto;
import com.ipi.pizzeria.entity.PizzaEntity;

import java.util.List;

public interface IPizzaService {

    // CRUD

    /** Transforme une entité Pizza en DTO */

    PizzaDto toDto(PizzaEntity entity);

    /** Récupère toutes les pizzas */
    List<PizzaDto> getAll();

    /** Récupère une pizza par son id, et renvoiyer null si elle n'existe pas */
    PizzaDto get(Long id);

    /** Vérifie les données,  nom pas vide, prix positif, au moins un ingrédient */

    Boolean estValide(PizzaAddDto dto);

    /** Vérifie que tous les id d'ingrédients existent en base */
    Boolean ingredientsExistent(List<Long> ingredientIds);

    /** Enregistre une nouvelle pizza avec ses ingrédients et renvoie le DTO*/
    PizzaDto ajouter(PizzaAddDto dto);

    /** Renvoie vrai si l'id existe dans la table pizza. */
    Boolean exist(Long id);

    /** Supprime la pizza  */
    void supprimer(Long id);

    // calcul metier

    /** Prix HT = Prix TTC / 1.055 */
    Double calculerPrixHT(Double prixTTC);

    /** TVA = Prix TTC - Prix HT */
    Double calculerTva(Double prixTTC);

    /** Somme des prix coûtants des ingrédients de la pizza */
    Double calculerCoutIngredients(PizzaEntity pizza);

    /** Marge brute = Prix HT - Coût total des ingrédients */
    Double calculerMargeBrute(PizzaEntity pizza);

    /** Détaille financier d'une pizza (GET /pizzas/{id}/financier) */
    PizzaFinancierDto getFinancier(Long id);

    /** Statistiques globales du catalogue (GET /pizzas/statistiques) */
    StatistiquesDto getStatistiques();
}