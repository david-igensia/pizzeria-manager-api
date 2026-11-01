package com.ipi.pizzeria.controller;


import com.ipi.pizzeria.dto.PizzaAddDto;
import com.ipi.pizzeria.service.IPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;

@RestController
@RequestMapping("pizzas")
public class PizzaController {

    @Autowired
    private IPizzaService service;

    @PostMapping
    public ResponseEntity ajouter(@RequestBody PizzaAddDto dto){
        if (!service.estValide(dto)) {
            return new ResponseEntity("Nom obligatoire, prix de vente positif et au moins un ingredient",
                    HttpStatusCode.valueOf(400));
        }
        if (!service.ingredientsExistent(dto.getIngredientIds())) {
            return new ResponseEntity(" Un ou plusieurs ingredients n'existe pas",
                    HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity(service.ajouter(dto), HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(service.getAll(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("statistiques")
    public ResponseEntity statistiques() {
        return new ResponseEntity(service.getStatistiques(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable Long id) {
        if (!service.exist(id)) {
            return new ResponseEntity("La pizza" + id + "n'existe pas", HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity(service.get(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping("{id}/financier")
    public ResponseEntity financier(@PathVariable Long id) {
        if (!service.exist(id)) {
            return new ResponseEntity("La pizza " + id + " n'existe pas", HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity(service.getFinancier(id), HttpStatusCode.valueOf(200));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity supprimer(@PathVariable Long id) {
        if (!service.exist(id)) {
            return new ResponseEntity("La pizza " + id + " n'existe pas", HttpStatusCode.valueOf(404));

        }
        service.supprimer(id);
        return new ResponseEntity("Pizza " + id + " supprimer", HttpStatusCode.valueOf(200));
    }
}
