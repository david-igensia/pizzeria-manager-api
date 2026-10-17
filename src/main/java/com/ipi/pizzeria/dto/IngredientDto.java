package com.ipi.pizzeria.dto;

public class IngredientDto {

    private Long id;
    private String nom;
    private Double prixCoutant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
