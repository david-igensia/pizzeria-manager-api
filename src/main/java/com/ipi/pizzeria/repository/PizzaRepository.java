package com.ipi.pizzeria.repository;


import com.ipi.pizzeria.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {

    boolean existsByIngredients_Id(Long ingredientsId);
}
