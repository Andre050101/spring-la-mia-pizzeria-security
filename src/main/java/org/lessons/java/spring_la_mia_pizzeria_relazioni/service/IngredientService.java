package org.lessons.java.spring_la_mia_pizzeria_relazioni.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.exception.NotFoundException;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository repoIng;

    public List<Ingredient> findAll() {
        return repoIng.findAll();
    }

    public Ingredient getById(Integer id) {
        Optional<Ingredient> ingredientAttempt = repoIng.findById(id);
        if (ingredientAttempt.isEmpty()) {
            // throw new NameNotFoundException();
            throw new IllegalArgumentException();
        }

        return ingredientAttempt.get();
    }

    public Ingredient create(Ingredient ingr) {
        return repoIng.save(ingr);
    }

    public Ingredient update(Ingredient ingr) {
        if (!repoIng.existsById(ingr.getId())) {
            throw new NotFoundException("Ingrediente con id " + ingr.getId() + " non trovato");
        }
        return create(ingr);
    }

    public void deleteById(Integer id) {
        Ingredient ingr = getById(id);
        repoIng.delete(ingr);
    }

}
