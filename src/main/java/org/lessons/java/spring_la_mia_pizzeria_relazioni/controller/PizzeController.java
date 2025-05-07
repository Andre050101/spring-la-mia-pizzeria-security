package org.lessons.java.spring_la_mia_pizzeria_relazioni.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Discount;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.DiscountRepository;
import org.lessons.java.spring_la_mia_pizzeria_relazioni.repository.PizzaRepository;
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
    private PizzaRepository repo;

    @Autowired
    private DiscountRepository repoDis;

    @GetMapping
    public String index(@RequestParam(name = "nome", required = false) String nome, Model model) {
        List<Pizza> pizze;
        if (nome != null && !nome.isBlank()) {
            pizze = repo.findByNomeContainingIgnoreCase(nome);
        } else {
            pizze = repo.findAll();
        }
        model.addAttribute("pizze", pizze);
        model.addAttribute("nome", nome);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizze", repo.findById(id).get());
        return "/pizzas/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/create";
        }

        // Salvataggio dato
        repo.save(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repo.findById(id).get());
        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/edit";
        }

        // Aggiornamento dato
        repo.save(formPizza);
        return "redirect:/pizzas";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Pizza pizza = repo.findById(id).get();
        for (Discount discountToDelete : pizza.getDiscounts()) {
            repoDis.delete(discountToDelete);
        }
        repo.deleteById(id);
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/addDiscount")
    public String addDiscount(@PathVariable Integer id, Model model) {
        Discount discount = new Discount();
        discount.setPizza(repo.findById(id).get());

        model.addAttribute("discount", discount);
        return "discounts/create";
    }

}
