package com.ipi.pizzeria.dto;

public class IngredientAddDto {

    private String nom;
    private Double prixCoutant;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrixCoutant() {
        return prixCoutant;
    }

    public void setPrixCoutant(Double prixCoutant) {
        this.prixCoutant = prixCoutant;
    }
}
