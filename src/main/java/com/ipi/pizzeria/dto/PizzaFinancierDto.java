package com.ipi.pizzeria.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"pizzaId", "nomPizza", "prixVenteTTC", "prixVenteHT",
        "tva5_5", "coutTotalIngredients", "margeBrute"})
public class PizzaFinancierDto {

    private Long pizzaId;
    private String nomPizza;
    private Double prixVenteTTC;
    private Double prixVenteHT;
    private Double tva5_5;
    private Double coutTotalIngredients;
    private Double margeBrute;

    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getNomPizza() {
        return nomPizza;
    }

    public void setNomPizza(String nomPizza) {
        this.nomPizza = nomPizza;
    }

    public Double getPrixVenteTTC() {
        return prixVenteTTC;
    }

    public void setPrixVenteTTC(Double prixVenteTTC) {
        this.prixVenteTTC = prixVenteTTC;
    }

    public Double getPrixVenteHT() {
        return prixVenteHT;
    }

    public void setPrixVenteHT(Double prixVenteHT) {
        this.prixVenteHT = prixVenteHT;
    }

    public Double getTva5_5() {
        return tva5_5;
    }

    public void setTva5_5(Double tva5_5) {
        this.tva5_5 = tva5_5;
    }

    public Double getCoutTotalIngredients() {
        return coutTotalIngredients;
    }

    public void setCoutTotalIngredients(Double coutTotalIngredients) {
        this.coutTotalIngredients = coutTotalIngredients;
    }

    public Double getMargeBrute() {
        return margeBrute;
    }

    public void setMargeBrute(Double margeBrute) {
        this.margeBrute = margeBrute;
    }
}
