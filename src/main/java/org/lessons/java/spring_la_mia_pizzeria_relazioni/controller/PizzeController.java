package org.lessons.java.spring_la_mia_pizzeria_relazioni.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.PizzaRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzeController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private DiscountRepository repoDis;

    @Autowired
    private IngredientRepository repoIng;

    @GetMapping
    public String index(@RequestParam(name = "nome", required = false) String nome, Model model) {
        List<Pizza> pizze;
        if (nome != null && !nome.isBlank()) {
            pizze = pizzaService.findByTitle(nome);
        } else {
            pizze = pizzaService.findAll();
        }
        model.addAttribute("pizze", pizze);
        model.addAttribute("nome", nome);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizze", pizzaService.getById(id));
        return "/pizzas/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", repoIng.findAll());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/create";
        }

        // Salvataggio dato
        pizzaService.create(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredients", repoIng.findAll());
        model.addAttribute("pizza", pizzaService.getById(id));
        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/edit";
        }

        // Aggiornamento dato
        pizzaService.update(formPizza);
        return "redirect:/pizzas";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Pizza pizza = pizzaService.getById(id);
        for (Discount discountToDelete : pizza.getDiscounts()) {
            repoDis.delete(discountToDelete);
        }
        pizzaService.deleteById(id);
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/addDiscount")
    public String addDiscount(@PathVariable Integer id, Model model) {
        Discount discount = new Discount();
        discount.setPizza(pizzaService.getById(id));

        model.addAttribute("discount", discount);
        return "discounts/create";
    }

}
