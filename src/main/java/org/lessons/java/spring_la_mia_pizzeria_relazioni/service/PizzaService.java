package org.lessons.java.spring_la_mia_pizzeria_relazioni.service;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.exception.NotFoundException;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepo;

    @Autowired
    private DiscountRepository discountRepo;

    public List<Pizza> findAll() {
        return pizzaRepo.findAll();
    }

    public List<Pizza> findAllSortedByTitle() {
        return pizzaRepo.findAll(Sort.by("nome"));
    }

    public Pizza getById(Integer id) {
        Optional<Pizza> pizzaAttempt = pizzaRepo.findById(id);
        if (pizzaAttempt.isEmpty()) {
            // throw new NameNotFoundException();
            throw new IllegalArgumentException();
        }

        return pizzaAttempt.get();
    }

    public List<Pizza> findByTitle(String title) {
        return pizzaRepo.findByNomeContainingIgnoreCase(title);
    }

    public Pizza create(Pizza pizza) {
        return pizzaRepo.save(pizza);
    }

    public Pizza update(Pizza pizza) {
        if (!pizzaRepo.existsById(pizza.getId())) {
            throw new NotFoundException("Pizza con id" + pizza.getId() + "non trovata!");
        }
        return pizzaRepo.save(pizza);
    }

    public void delete(Pizza pizza) {
        for (Discount discountToDelete : pizza.getDiscounts()) {
            discountRepo.delete(discountToDelete);
        }

        pizzaRepo.delete(pizza);
    }

    public void deleteById(Integer id) {
        Pizza pizza = getById(id);
        for (Discount discountToDelete : pizza.getDiscounts()) {
            discountRepo.delete(discountToDelete);
        }

        pizzaRepo.delete(pizza);

    }

    public Boolean existById(Integer id) {
        return pizzaRepo.existsById(id);
    }

    public Boolean exist(Pizza pizza) {
        return existById(pizza.getId());
    }

    public Optional<Pizza> findById(Integer id) {
        return pizzaRepo.findById(id);
    }

}
