package com.ipi.pizzeria.dto;

public class IngredientAddDto {

    private String nom;
    private Double prixCoutant;

    public Double getPriCoutant() {
        return prixCoutant;
    }

    public void setPrixCoutant(Double priCoutant) {
        this.prixCoutant = priCoutant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
