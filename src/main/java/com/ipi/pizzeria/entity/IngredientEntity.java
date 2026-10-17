package com.ipi.pizzeria.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prix_coutant", nullable = false)
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
