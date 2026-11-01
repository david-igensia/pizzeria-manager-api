package com.ipi.pizzeria.controller;


import com.ipi.pizzeria.dto.IngredientAddDto;
import com.ipi.pizzeria.service.IIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ingredients")
public class IngredientController {

    @Autowired
    private IIngredientService service;

    @PostMapping()
    public ResponseEntity ajouter(@RequestBody IngredientAddDto dto) {
        if (!service.estValide(dto)) {
            return  new ResponseEntity("Nom obligatoire et prix coutant positif",
                    HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity(service.ajouter(dto),
                HttpStatusCode.valueOf(201));

    }

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(service.getAll(), HttpStatusCode.valueOf(200));

    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable Long id){
        if (!service.exist(id)) {
            return new ResponseEntity("l'ingrédient " + id + " N'existe pas",
                   HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity(service.get(id), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("{id}")
    public ResponseEntity supprimer(@PathVariable Long id) {
        if (!service.exist(id)) {
            return new ResponseEntity("ingredient "  + id +  " n'existe pas",
                    HttpStatusCode.valueOf(404));
        }
        if (service.estUtilise(id)) {
            return new ResponseEntity("Impossible : l'ingrédient " + id + " est utilisé dans au moins une pizza",
                    HttpStatusCode.valueOf(409));
        }
        service.supprimer(id);
        return new ResponseEntity("Ingredient " + id + " supprime",
                HttpStatusCode.valueOf(200));
    }
}
