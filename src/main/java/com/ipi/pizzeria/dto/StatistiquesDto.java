package com.ipi.pizzeria.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"nbPizzas", "prixMoyenVenteTTC",
        "pizzaLaPlusRentable", "pizzaLaMoinsRentable"})

public class StatistiquesDto {

    private Integer nbPizzas;
    private Double prixMoyenVenteTTC;
    private String pizzaLaPlusRentable;
    private String pizzaLaMoinsRentable;

    public Integer getNbPizzas() {
        return nbPizzas;
    }

    public void setNbPizzas(Integer nbPizzas) {
        this.nbPizzas = nbPizzas;
    }

    public Double getPrixMoyenVenteTTC() {
        return prixMoyenVenteTTC;
    }

    public void setPrixMoyenVenteTTC(Double prixMoyenVenteTTC) {
        this.prixMoyenVenteTTC = prixMoyenVenteTTC;
    }

    public String getPizzaLaPlusRentable() {
        return pizzaLaPlusRentable;
    }

    public void setPizzaLaPlusRentable(String pizzaLaPlusRentable) {
        this.pizzaLaPlusRentable = pizzaLaPlusRentable;
    }

    public String getPizzaLaMoinsRentable() {
        return pizzaLaMoinsRentable;
    }

    public void setPizzaLaMoinsRentable(String pizzaLaMoinsRentable) {
        this.pizzaLaMoinsRentable = pizzaLaMoinsRentable;
    }
}
